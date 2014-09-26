package com.honeytv.task;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.honeytv.utils.HttpUtil;

public abstract class AbstractPostAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>{

	private HttpUtil mHttpUtil;
	private Gson gson;
	private Class<Result> result; 

	protected AsyncCallBack mAsyncCallBack;
	
	public AbstractPostAsyncTask(Class<Result> t, AsyncCallBack asyncCallBack) {
		this.mAsyncCallBack = asyncCallBack;
		this.result = t;
		this.gson = new Gson();
		this.mHttpUtil = new HttpUtil();
	}
	

	@Override
	protected Result doInBackground(Params... params) {
		InputStream is = mHttpUtil.sendHttpClientPostDataRequest(getRequestData(params), getDownloadUrl());
		return parse(is, result);
	}
	
	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		this.mAsyncCallBack.onPostExecute(result);
	}
	
	
	private Result parse(InputStream is, Class<Result> t) {
		Result t1 = null;
		try {
			Reader reader = new InputStreamReader(new BufferedInputStream(is));
			t1 = gson.fromJson(reader, t);
			return t1;
		} catch (Exception e) {

		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {

			}
		}
		return t1;
	}
	
	public abstract String[][] getRequestData(Params... params);
	
	public abstract String getDownloadUrl();
}
