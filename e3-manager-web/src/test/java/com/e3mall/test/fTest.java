package com.e3mall.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class fTest {
	
	@Test
	public void testUpload() throws Exception{
		//创建全局对象加载配置文件
		ClientGlobal.init("H:/MarsWorkSpace/e3-manager-web/src/main/resources/conf/client.conf");
		//创建trackerClient对象
		TrackerClient trackerClient=new TrackerClient();
		//通过trackerClient获得一个trackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//常见一个StorageClient对象的引用
		StorageServer storageServer=null;
		//创建一个StorageCLient封装StorageServer和TrackerServer
		StorageClient storageClient=new StorageClient(trackerServer, storageServer);
		//上传文件
		String[] strings = storageClient.upload_file("D:/B2C/e3商城_day01/黑马32期/01.教案-3.0/01.参考资料/广告图片/9a80e2d06170b6bb01046233ede701b3.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
		
	}
	
}
