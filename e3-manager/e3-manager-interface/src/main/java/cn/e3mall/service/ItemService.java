package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.UpdateItemData;

public interface ItemService {
	
	
	public TbItem getItemById(long id);
	
	public EasyUIDataGridResult getItemList(int page,int rows);
	
	public E3Result insertTbItem(TbItem item,String desc);
	
	public TbItemDesc geTbItemDesc(long id);
	
	public E3Result updateIteamData(UpdateItemData data);
	
	public int updateIteam(TbItem item);
	
	public int updateItemDesc(TbItemDesc itemDesc);
	public E3Result deleteItems(String []ids);
	
}
