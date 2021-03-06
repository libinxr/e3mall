package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.content.service.ContentCatgoryService;

@Controller
public class ContentCatgoryController {

	@Autowired
	ContentCatgoryService contentCatgoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(
			@RequestParam(value="id",defaultValue="0") long parentId){
		List<EasyUITreeNode> catList = contentCatgoryService.getContentCatList(parentId);
		return catList;
	}
	
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public E3Result createContentCatgory(long parentId,String name){
		E3Result result = contentCatgoryService.insertContentCatgroy(parentId, name);
		return result;
	}
	
	@RequestMapping(value="/content/category/delete/",method=RequestMethod.POST)
	@ResponseBody
	public E3Result deleteContentCatgory(long id){
		E3Result result = contentCatgoryService.deleteContentCatgory(id);
		return result;
		
	}
	
}
