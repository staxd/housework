package com.houseWork.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

public class PayUtils {

	@SuppressWarnings("deprecation")
	public static String post(String url, String xmlParam,String KEY_PATH,String mch_id){
        StringBuilder sb = new StringBuilder();
         try {
                KeyStore keyStore  = KeyStore.getInstance("PKCS12");
                FileInputStream instream = new FileInputStream(new File(KEY_PATH));
                try {
                    keyStore.load(instream, mch_id.toCharArray());
                } finally {
                    instream.close();
                }
          
                // 证书
                SSLContext sslcontext = SSLContexts.custom()
                        .loadKeyMaterial(keyStore, mch_id.toCharArray())
                        .build();
                // 只允许TLSv1协议
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                        sslcontext,
                        new String[] { "TLSv1" },
                        null,
                        SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
                //创建基于证书的httpClient,后面要用到
                CloseableHttpClient client = HttpClients.custom()
                        .setSSLSocketFactory(sslsf)
                        .build();
                 
             HttpPost httpPost = new HttpPost(url);//退款接口
             StringEntity  reqEntity  = new StringEntity(xmlParam);
             // 设置类型
             reqEntity.setContentType("application/x-www-form-urlencoded");
             httpPost.setEntity(reqEntity);
             CloseableHttpResponse response = client.execute(httpPost);
             try {
                 HttpEntity entity = response.getEntity();
                 System.out.println(response.getStatusLine());
                 if (entity != null) {
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                     String text="";
                     while ((text = bufferedReader.readLine()) != null) {
                         sb.append(text);              
                     }
                 }
                 EntityUtils.consume(entity);
             } catch(Exception e){
                 e.printStackTrace();
             }finally {
                 try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
             }
         } catch (Exception e) {
                e.printStackTrace();
        }
    
		return sb.toString();

}
	
}
