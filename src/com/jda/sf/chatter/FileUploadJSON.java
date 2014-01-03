package com.jda.sf.chatter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.*;

import com.fasterxml.jackson.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileUploadJSON {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws HttpException, IOException {
		// TODO Auto-generated method stub
		String oauthToken = "00DQ0000001f30n!ARcAQCVbMyBnWMKxImlSnmI3I12qbJNhoM__16WHLxfX6YVyXqbC3lyCQA66Uhu0YHyUttlYTiDzkKDkLnI8eESveagFEEhm";
		String url = "https://cs22.salesforce.com/services/data/v27.0/chatter/feeds/record/006Q000000BQESF/feed-items";
		//String text = "I love posting files to Chatter!";
		Map<String, List<Map<String, String>>> messageSegments = new HashMap<String, List<Map<String, String>>>();
		Map<String, Map<String, List<Map<String, String>>>> json = new HashMap<String, Map<String, List<Map<String, String>>>>();
		Map<String, Map<String,String>> attch = new HashMap<String, Map<String,String>>();
		
		List<Map<String, String>> segments = new LinkedList<Map<String, String>>();
		Map<String, String> s = new HashMap<String, String>();
		s.put("type","mention");
		s.put("id", "00570000001bCyB");
		segments.add(s);
		Map<String, String> s1 = new HashMap<String, String>();
		s1.put("type","text");
		s1.put("text", "Will it mention?");		
		segments.add(s1);
		
		messageSegments.put("messageSegments", segments);
		
		Map<String, String> s2 = new HashMap<String, String>();
				
		s2.put("attachmentType", "NewFile");
		s2.put("description", "API Test");
		s2.put("title", "Content File 1");
		
		attch.put("attachment", s2);
		json.put("body", messageSegments);
		
		ObjectMapper mapper=new ObjectMapper();
		String totalString =  "{ \"body\":"+mapper.writeValueAsString(messageSegments) + ", " + mapper.writeValueAsString(attch).substring(1,mapper.writeValueAsString(attch).length()-1)+"}";
		System.out.print(totalString);
		//mapper.writeValueAsString(json);
		
		File contentFile = getFile();
		
		
		StringPart jsonPart1=new StringPart("json",totalString);
		//Json
		jsonPart1.setContentType("application/json");
		jsonPart1.setCharSet("UTF-8");
		FilePart filePart= new FilePart("feedItemFileUpload", contentFile);
		filePart.setContentType("application/pdf");		
		Part[] parts = {		    
		    jsonPart1,		    
		    filePart
		};

		final PostMethod postMethod = new PostMethod(url);
		
		try {		  
		  //postMethod.setRequestEntity(new StringRequestEntity(mapper.writeValueAsString(json), "application/json", "UTF-8"));
		  postMethod.setRequestEntity(new MultipartRequestEntity(parts, postMethod.getParams()));
		  postMethod.setRequestHeader("Authorization", "OAuth " + oauthToken);
		  postMethod.addRequestHeader("X-PrettyPrint", "1");
		  //postMethod.addRequestHeader("Content-Type","application/json");
		  HttpClient httpClient = new HttpClient();  
		  
		  
		  httpClient.getParams().setSoTimeout(60000);
		  //System.out.println(postMethod.getQueryString());
		  int returnCode = httpClient.executeMethod(postMethod);
		  System.out.println(postMethod.getResponseBodyAsString()+returnCode);
		 // System.out.println("Expected return code of: " + HttpStatus.SC_CREATED, returnCode == HttpStatus.SC_CREATED);
		} finally {
		  postMethod.releaseConnection();
		}
	}

	private static File getFile() {
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\Desktop\\Predictor\\Simple Works.pdf");
		return file;
	}

}



