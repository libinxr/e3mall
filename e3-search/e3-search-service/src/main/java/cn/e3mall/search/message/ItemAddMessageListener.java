package cn.e3mall.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;

public class ItemAddMessageListener implements MessageListener {

	@Autowired
	ItemMapper ItemMapper;
	@Autowired
	SolrServer solrServer;
	
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage=(TextMessage) message;
		//获取商品id
		try {
			String text = textMessage.getText();
			long itemId = new  Long(text);
			Thread.sleep(1000);
			//查询商品信息
			SearchItem searchItem = ItemMapper.getItemById(itemId);
			//创建文档对象
			SolrInputDocument document=new SolrInputDocument();
			//向文档对象中添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			//将域同步到索引库
			solrServer.add(document);
			//提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
