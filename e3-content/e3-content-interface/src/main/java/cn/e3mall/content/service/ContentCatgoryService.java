package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbContentCategory;

public interface ContentCatgoryService {

	
	public List<EasyUITreeNode> getContentCatList(long parentId);
	
	public E3Result insertContentCatgroy(long parentId,String name);
	
	public E3Result deleteContentCatgory(long id);
	
}
