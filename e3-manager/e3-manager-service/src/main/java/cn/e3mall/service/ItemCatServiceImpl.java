package cn.e3mall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	TbItemCatMapper catMapper;
	
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> catItemList = catMapper.selectByExample(example);
		List<EasyUITreeNode> resultList=new ArrayList<>();
		
		for (TbItemCat tbItemCat : catItemList) {
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

}
