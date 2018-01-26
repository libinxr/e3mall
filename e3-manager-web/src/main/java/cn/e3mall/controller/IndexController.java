package cn.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 后台页面跳转
 * 
 */

@Controller
public class IndexController {

	@RequestMapping("/")
	public String gotoIndex(){
		return "index";
	}
	
	@RequestMapping("/{page_index}")
	public String getPage(@PathVariable String page_index){
		return page_index;
	}
	
}
