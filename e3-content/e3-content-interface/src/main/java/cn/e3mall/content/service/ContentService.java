package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbContent;

public interface ContentService {

	
	public E3Result insertContent(TbContent content);
	
	public List<TbContent> getContentListByCid(long cid);
	
}
