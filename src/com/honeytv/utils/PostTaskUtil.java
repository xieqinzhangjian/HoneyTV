package com.honeytv.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.util.Log;

import com.google.gson.Gson;

public class PostTaskUtil {
	public static final String TAG="PostTaskUtil";
	private static HttpUtil httpUtil;
	private static Gson gson = new Gson();
	
	@SuppressWarnings("unchecked")
	public static <T>T parseResult(InputStream is, Class<?> t) {
		T t1 = null;
		try {
			Reader reader = new InputStreamReader(new BufferedInputStream(is));
			t1 = (T) gson.fromJson(reader, t);
			return t1;
		} catch (Exception e) {
			Log.e(TAG, "解析返回的登录Url失败:" + e.toString());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				Log.e(TAG, "关闭JSON流失败:" + e.getMessage());
			}
		}
		return t1;
	}

	public static InputStream sendHttpPostRequest(String[][] data, String url) {
		httpUtil = new HttpUtil();
		Log.i(TAG, "登录发送post请求");
		Log.i(TAG, "url地址" + url);
		return httpUtil.sendHttpClientPostDataRequest(data, url);
	}
}
