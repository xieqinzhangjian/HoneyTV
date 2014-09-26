package com.honeytv.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class HttpUtil {
	private static final String TAG = "HttpUtil";
	 
	public InputStream sendHttpClientPostDataRequest(String[][] data, String str) {
		Map<String, String> params = new HashMap<String, String>();
		for(int  x = 0; x < data.length; x ++){
			params.put(data[x][0], data[x][1]);
		}
		try {
			return sendHttpClientPostRequest(str, params);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public InputStream sendHttpClientPostRequest(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();					//存放请求参数
		if(params != null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()) {
				pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			} 
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, HTTP.UTF_8);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		return sendHttpRequest(httpPost);
	}

	private InputStream sendHttpRequest(HttpUriRequest httpRequest) throws IOException,
			ClientProtocolException {
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
		int code = httpResponse.getStatusLine().getStatusCode();
		if (code == HttpStatus.SC_OK) {
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
            	Log.i(TAG, "HTTP("+httpRequest.getMethod()+")发送请求["+httpRequest.getURI()+"] 正常返回");

                return httpEntity.getContent();
            } else {
            	Log.e(TAG, "HTTP("+httpRequest.getMethod()+")请求["+httpRequest.getURI()+"]不正常，Status Code 200, entity为空");
            }
        } else {
        	Log.e(TAG, "HTTP("+httpRequest.getMethod()+")请求["+httpRequest.getURI()+"]不正常，Status Code :" + code);
        }
		return null;
	}
	
	public InputStream sendHttpClientGetRequest(String url) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		return sendHttpRequest(httpGet);
	}
	
	
}
