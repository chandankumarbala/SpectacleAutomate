package com.specAuto.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HtmlConversionService {

	@Value("${swagger.json.upload.folder}")
	String uploadFolderAbsPath;

	@Value("${spectacle.generated.html.folder}")
	String spectaleHtml;
	
	
	public String convertTOEmbedebleHtml(String fileName){
		
		String status="fail";
		Runtime rt = Runtime.getRuntime();
		String[] commands = {"spectacle.cmd","-e",uploadFolderAbsPath+fileName,"-t",spectaleHtml,"-f",fileName+".html","-C","true","-J","true"};
		
		try{
			Process proc = rt.exec(commands);
			BufferedReader stdInput = new BufferedReader(new 
				     InputStreamReader(proc.getInputStream()));

				BufferedReader stdError = new BufferedReader(new 
				     InputStreamReader(proc.getErrorStream()));

				// read the output from the command
				System.out.println("Here is the standard output of the command:\n");
				String s = null;
				while ((s = stdInput.readLine()) != null) {
				    System.out.println(s);
				}

				// read any errors from the attempted command
				System.out.println("Here is the standard error of the command (if any):\n");
				while ((s = stdError.readLine()) != null) {
				    System.out.println(s);
				}
			
				if(proc.exitValue()==0)
					status="success";
		}catch(Exception e){
			return status;
		}
		
		
		return status;
	}
}
