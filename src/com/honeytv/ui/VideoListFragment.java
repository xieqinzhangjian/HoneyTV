/**   
 * Copyright © 2014 All rights reserved.
 * 
 * @Title: SlidingPaneContentFragment.java 
 * @Prject: SlidingPane
 * @Package: com.example.slidingpane 
 * @Description: TODO
 * @author: raot  719055805@qq.com
 * @date: 2014年9月5日 上午10:44:01 
 * @version: V1.0   
 */
package com.honeytv.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.honeytv.entity.VideoList;
import com.honeytv.entity.VideoList.VideoInfo;
import com.honeytv.task.AsyncCallBack;
import com.honeytv.utils.ACache;
import com.honeytv.utils.GsonTools;
import com.honeytv.utils.HttpUtil;
import com.honeytv.utils.ImageUtil;
import com.honeytv.utils.NetUtil;
import com.honeytv.utils.StreamTools;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: VideoListFragment
 * @Description: TODO
 * @author: raot 290605830@qq.com
 * @date: 2014年9月25日
 */
@SuppressLint("NewApi")
public class VideoListFragment extends Fragment {
	private View lb_video_list;
	private ListView video_list;

	private Context context;
	private LayoutInflater inflater;
	private ACache mCache;
	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		lb_video_list.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) lb_video_list.getLayoutParams();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		lb_video_list = inflater.inflate(R.layout.lb_video_list, container, false);
		context = getActivity();
		initView();
		mCache = ACache.get(getActivity());
		// initData();

		// 初始化数据
		
		if(!NetUtil.isNetworkAvailable(getActivity())){
			Toast.makeText(getActivity(), "网络不通请检查", Toast.LENGTH_LONG).show();
		}
		VideoListTask vlt = new VideoListTask();
		vlt.execute(getString(R.string.vodeo_list_url));


		return lb_video_list;
	}

	public void initView() {
		video_list = (ListView) lb_video_list.findViewById(R.id.lv_video_list);
		video_list.setOnItemClickListener(new MyOnItemClickListener());

	}

	class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			System.out.println("position" + position);
			TextView tv = (TextView) view.findViewById(R.id.tv_title);
			System.out.println("tv" + tv.getText());

		}
	}

	public HttpUtil httpUtil = null;

	public class VideoListTask extends AsyncTask<String, Void, VideoList> {
		private static final String TAG = "VideoListTask";
		@Override
		protected VideoList doInBackground(String... params) {
			if(mCache.getAsObject("lb_videoList")!=null){
				Log.i(TAG, "用缓存里的用户信息显示...");
				return (VideoList) mCache.getAsObject("lb_videoList");
			}
			httpUtil = new HttpUtil(); 
			VideoList vl = null;
			try {
				if (NetUtil.isNetworkAvailable(getActivity())) {
					Log.i(TAG, "请求视频列表:" + params[0]);
					InputStream is = httpUtil.sendHttpClientGetRequest(params[0]);
					String data = StreamTools.readFromStream(is);
					vl = GsonTools.changeGsonToBean(data, VideoList.class);
				}else{
					return mCache.getAsObject("lb_videoList")==null?null:(VideoList)(mCache.getAsObject("lb_videoList"));
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return vl;
		}

		InputStream is = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 主要是更新UI操作
		@Override
		protected void onPostExecute(VideoList lv) {
			if(lv!=null){
				if (lv.code == 0) {
					List<VideoInfo> lb_videoList = lv.videoList;
					for (final VideoInfo videoInfo : lb_videoList) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("iv_video_icon", R.drawable.video1);
						map.put("tv_title", videoInfo.title);
						list.add(map);
					}
					SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.video_list_item,
							new String[] { "iv_video_icon", "tv_title" }, new int[] { R.id.iv_video_icon, R.id.tv_title });
					video_list.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					mCache.put("lb_videoList",lv,60*30);
					
				} else {
					Toast.makeText(getActivity(), lv.message, 0).show();  

				}
			}

		}
	}

}
