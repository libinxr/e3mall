package cn.e3mall.content.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.parser.ContentModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Data;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;


@Service
public class ContentCatgoryServiceImpl implements ContentCatgoryService{

	@Autowired
	TbContentCategoryMapper contentCatgoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCatgoryMapper.selectByExample(example);
		List<EasyUITreeNode> treeList=new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode treeNode=new EasyUITreeNode();
			treeNode.setId(tbContentCategory.getId());
			treeNode.setText(tbContentCategory.getName());
			treeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			treeList.add(treeNode);
		}
		return treeList;
	}

	@Override
	public E3Result insertContentCatgroy(long parentId, String name) {
		TbContentCategory contentCategory=new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//新添加的节点一定是叶子节点
		contentCategory.setIsParent(false);
		contentCatgoryMapper.insert(contentCategory);
		//对其父节点进行设置
		TbContentCategory parent = contentCatgoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent())
		{
			parent.setIsParent(true);
			contentCatgoryMapper.updateByPrimaryKey(parent);
		}
		
		return E3Result.ok(contentCategory);
	}

	@Override
	public E3Result deleteContentCatgory(long id) {
		//查询当前节点是否为父节点
		TbContentCategory selectByPrimaryKey = contentCatgoryMapper.selectByPrimaryKey(id);
		long parentId=selectByPrimaryKey.getParentId();
		
		//查询出当前节点父节点的所有子节点的子节点
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCatgoryMapper.selectByExample(example);
		
		//分两种情况，是父节点与不是父节点
		if(!selectByPrimaryKey.getIsParent())
			contentCatgoryMapper.deleteByPrimaryKey(id);
		else
		{
			for (TbContentCategory tbContentCategory : list) {
				contentCatgoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
			}
			contentCatgoryMapper.deleteByPrimaryKey(id);
		}
		if(list.size()==0)
		{
			TbContentCategory contentCategory = contentCatgoryMapper.selectByPrimaryKey(parentId);
			contentCategory.setIsParent(false);
			contentCatgoryMapper.updateByPrimaryKey(contentCategory);
		}
		
		return E3Result.ok();
	}

}
