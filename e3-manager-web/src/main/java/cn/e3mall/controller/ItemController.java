package cn.e3mall.controller;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.xml.ws.RespectBinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.IDUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.UpdateItemData;
import cn.e3mall.service.ItemService;

/**
 * 商品Controller
 * @author 斌
 *
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	
	
	
	@RequestMapping("/rest/item/param/item/query/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId){
		
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows){
		EasyUIDataGridResult itemList = itemService.getItemList(page, rows);
		return itemList;
	}
	
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result insertItem(TbItem item,String desc){
		final long itemId=IDUtils.genItemId();
		item.setId(itemId);
		E3Result result = itemService.insertTbItem(item, desc);
		
		jmsTemplate.send(topicDestination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				
				return session.createTextMessage(itemId+"");
			}
		});
		return result;
	}
	
	@RequestMapping("/rest/item/query/item/desc/{itemId}")
	@ResponseBody
	public TbItemDesc geTbItemDescById(@PathVariable long itemId){
		
		TbItemDesc itemDesc = itemService.geTbItemDesc(itemId);
		return itemDesc;
	}
	
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public E3Result updateItemData(UpdateItemData updateItemData){
		E3Result result = itemService.updateIteamData(updateItemData);
		return result;
	
	}
	
	@RequestMapping(value="/rest/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public E3Result deleteItemData(@RequestParam("ids") String[] ids){
		E3Result result = itemService.deleteItems(ids);
		return result;
	}
}




