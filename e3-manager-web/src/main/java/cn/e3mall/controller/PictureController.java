package cn.e3mall.controller;

import java.io.ObjectOutputStream.PutField;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.util.FastDFSClient;
import cn.e3mall.common.util.JsonUtils;

@Controller
public class PictureController {

	@Value("${IMG_SERVER_URL}")
	private String IMG_SERVER_URL;
	
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile){
		Map resultMap=new HashMap<>();
		try {
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			//获取文件的扩展名
			String filename = uploadFile.getOriginalFilename();
			String extName=filename.substring(filename.lastIndexOf(".")+1);
			//上传图片
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			url=IMG_SERVER_URL+url;
			resultMap.put("error", 0);
			resultMap.put("url", url);
			return JsonUtils.objectToJson(resultMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", 1);
			resultMap.put("url", "图片上传失败");
			return JsonUtils.objectToJson(resultMap);
		}
		
	}
	
}
