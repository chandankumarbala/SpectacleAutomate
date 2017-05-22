package com.specAuto.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.specAuto.service.HtmlConversionService;


@RestController
@RequestMapping("/api")
public class SpecController {
	
	@Autowired HtmlConversionService htmlCOnvert;

	@RequestMapping(value="/swaggerUpload", method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> uploadSwaggerDocs(@RequestParam("swaggerFile") MultipartFile swaggerJson) throws JSONException{
		
		JSONObject resp = new JSONObject();
		
		String fileName;
		if(!swaggerJson.isEmpty()){
			
			 try {
				 	String hiddenFileName=swaggerJson.getOriginalFilename().replace(".json",  new Long( new Date().getTime()).toString()+".json");
				    fileName="src/main/resources/uploadFolder/"+hiddenFileName;
	                byte[] bytes = swaggerJson.getBytes();
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
	                stream.write(bytes);
	                stream.close();
	                resp.put("status", "uploaded");
	                resp.put("fileName", hiddenFileName);
	            } catch (Exception  e) {
	            	resp.put("status", "failed");
	            }
			
		}else{
			resp.put("status", "failed");
			
		}
		
		return new ResponseEntity(resp.toString(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/approve", method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> approveAndGenerateHtml(@RequestParam("request") String approval,@RequestParam(name="fileName",required=false) String targetFile) throws JSONException{
		
		JSONObject resp = new JSONObject();
		if(approval.equals("ok")){
			resp.put("status", htmlCOnvert.convertTOEmbedebleHtml(targetFile));	
		}else{
			resp.put("status", "discardedJson");
		}
		return new ResponseEntity(resp.toString(),HttpStatus.OK);
	}
	
}
