package com.jda.sf.chatter;
import java.io.File;
import java.io.IOException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;

public class FileUpload {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws HttpException, IOException {
		// TODO Auto-generated method stub
		String oauthToken = "00DQ0000001f30y!ARcAQERZPayrNLaOZhOXqH0lbrlTF4sV5iwmc6bzH8Y9VP6uhv77gL9.8YZuT8l1d4e4Km7nVosNbanBaXnQxKV16Res7uZl";
		String url = "https://cs3.salesforce.com/services/data/v27.0/chatter/feeds/record/006Q000000BQESF/feed-items";
		//String text = "I love posting files to Chatter!";
		File contentFile = getFile();
		String desc = "This is a test file that I'm posting.";
		String fileName = "contentFile 1";		
		StringPart jsonPart = new StringPart("title",fileName);

		//jsonPart.setContentType("application/json");
		FilePart filePart= new FilePart("feedItemFileUpload", contentFile);
		filePart.setContentType("application/pdf");
		Part[] parts = {
		    new StringPart("description", desc),
		    new StringPart("text","Will it mention?"),
		    new StringPart("mention","00570000001bCyB"),
		    //new StringPart("id",""),
		    jsonPart,
		    new StringPart("attachmentType", "NewFile"),
		    filePart
		};

		final PostMethod postMethod = new PostMethod(url);
		
		try {
		  postMethod.setRequestEntity(new MultipartRequestEntity(parts, postMethod.getParams()));
		  postMethod.setRequestHeader("Authorization", "OAuth " + oauthToken);
		  postMethod.addRequestHeader("X-PrettyPrint", "1");
		 // postMethod.addRequestHeader("Content-Type","multipart/form-data");
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
		File file = new File("C:\\Users\\j1012748\\Desktop\\Predictor\\Simple Works.pdf");
		return file;
	}

}




