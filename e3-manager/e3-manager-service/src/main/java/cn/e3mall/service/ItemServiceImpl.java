package cn.e3mall.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemDescExample;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.pojo.UpdateItemData;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	
	
	@Override
	public TbItem getItemById(long id) {
		//TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		TbItemExample example=new TbItemExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andIdEqualTo(id);
		//执行查询
		List<TbItem> selectByExample = tbItemMapper.selectByExample(example);
		if(selectByExample!=null)
			return selectByExample.get(0);
		return null;
	}


	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example=new TbItemExample();
		List<TbItem> list = new ArrayList<>();
		list=tbItemMapper.selectByExample(example);
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		//取分页结果
		PageInfo<TbItem> pageInfo=new PageInfo<>(list);
		long total = pageInfo.getTotal();
		result.setTotal(total);
		result.setRows(list);
		return result;
	}


	@Override
	public E3Result insertTbItem(TbItem item, String desc) {
//		//获取商品id
//		final long itemId = IDUtils.genItemId();
//		item.setId(itemId);
		//设置商品状态
		//1.正常 2.下架 3.删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入数据
		tbItemMapper.insert(item);
		//插入描述
		TbItemDesc dItemDesc=new TbItemDesc();
		dItemDesc.setItemId(item.getId());
		dItemDesc.setItemDesc(desc);
		dItemDesc.setCreated(new Date());
		dItemDesc.setUpdated(new Date());
		tbItemDescMapper.insert(dItemDesc);
		
		
		return E3Result.ok();
	}


	@Override
	public TbItemDesc geTbItemDesc(long id) {
		
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		
		return itemDesc;
	}


	@Override
	public E3Result updateIteamData(UpdateItemData data) {
		E3Result e3Result=new E3Result();
		TbItem item=new TbItem();
		item=data.getItem();
		TbItemDesc itemDesc=data.getTbItemDesc();
		int result1 = updateIteam(item);
		int result2 = updateItemDesc(itemDesc);
		if((result1+result2)!=2)
			return e3Result;
		return e3Result.ok();
		
	}


	@Override
	public int updateIteam(TbItem item) {
		
		TbItemExample example=new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(item.getId());
		int result = tbItemMapper.updateByExample(item, example);
		return result;
	}


	@Override
	public int updateItemDesc(TbItemDesc itemDesc) {
		TbItemDescExample example=new TbItemDescExample();
		cn.e3mall.pojo.TbItemDescExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemDesc.getItemId());
		int result = tbItemDescMapper.updateByExample(itemDesc, example);
		return result;
		
	}


	@Override
	public E3Result deleteItems(String[] ids) {
		int result=0;
		E3Result e3Result=new E3Result();
		for (String string : ids) {
			long id=Long.valueOf(string);
			result+= tbItemMapper.deleteByPrimaryKey(id);
		}
		if(result!=0)
			return e3Result.ok();
		return e3Result;
	}

}
