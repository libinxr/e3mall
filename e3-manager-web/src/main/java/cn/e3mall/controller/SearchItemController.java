package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.search.service.ItemSearchService;

@Controller
public class SearchItemController {
	
	
	@Autowired
	ItemSearchService itemSeachService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result updateSolr(){
		E3Result result = itemSeachService.getItemList();
		return result;
	}

}
