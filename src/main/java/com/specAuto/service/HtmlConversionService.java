package com.specAuto.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

@Component
public class HtmlConversionService {

	
	public String convertTOEmbedebleHtml(String fileName){
		String command="cmd C:/Users/vandana/AppData/Roaming/npm/spectacle.cmd -e "+" src/main/resources/uploadFolder/"+fileName+" -t src/main/resources/htmlConv "+" -f "+fileName+".html";
		String fullOutput=null; 
		try{
			 Process p=Runtime.getRuntime().exec(command); 
             p.waitFor(); 
             BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
             String line;
             while((line = reader.readLine()) != null) 
             { 
            	 fullOutput+=line;
             } 

             System.out.println(fullOutput);
		}catch(Exception e){
			
		}
		return fullOutput;
	}
}
