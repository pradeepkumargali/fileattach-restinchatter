package com.jda.sf.chatter;
import java.io.File;
import java.nio.charset.Charset;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
public class T2 {

    // TODO:change node from ap1 to your node
    private static final String RES_URL = "https://cs22.salesforce.com/services/data/v27.0/chatter/feeds/user-profile/me/feed-items";
    // TODO:change this before run
    private static final String OAUTH_TOKEN = "OAuth 00DQ0000001f30m!ARcAKRPZPayrNLaOZhOXqH0lbrlTF4sV5iwmc6bzH8Y9VP6uhv77gL9.8YZuT8l1d4e4Km7nVosNbanBaXnQxKV16Res7uZl";

    public static void main(String[] args) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost;
        MultipartEntity reqEntity;
        httppost = new HttpPost(RES_URL);
        reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        File imageFile = new File(
                "C:\\Desktop\\in_progress.png");
        FileBody bin = new FileBody(imageFile);
        reqEntity.addPart("feedItemFileUpload", bin);
        String fileName = "Prepare DIE Sheet.png";
        // file name can be text plain only, though using text/html doesn't breaks
        reqEntity.addPart("fileName", new StringBody(fileName, "text/plain",
                Charset.defaultCharset()));
        // Sending text/html doesn't helps as HTML will be printed, though using text/html doesn't breaks
        reqEntity.addPart("json", new StringBody("Hellow World", "text/plain",
                Charset.defaultCharset()));
        reqEntity.addPart("feedItemFileUpload", new FileBody(imageFile,
                fileName, "application/octet-stream", Charset.defaultCharset()
                        .toString()));
        httppost.setEntity(reqEntity);

        httppost.setHeader("Authorization", OAUTH_TOKEN);

        String response = EntityUtils.toString(httpclient.execute(httppost)
                .getEntity(), "UTF-8");

        System.out.println(response);
        // Time to see the glorious post in web browser
    }
}



