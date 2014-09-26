/**   
 * Copyright © 2014 All rights reserved.
 * 
 * @Title: SlidingPaneMenuFragment.java 
 * @Prject: SlidingPane
 * @Package: com.example.slidingpane 
 * @Description: TODO
 * @author: raot  719055805@qq.com
 * @date: 2014年9月5日 上午10:42:07 
 * @version: V1.0   
 */
package com.honeytv.ui;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.tencent.qq.QQ;

import com.honeytv.entity.Users;
import com.honeytv.utils.ACache;
import com.lidroid.xutils.BitmapUtils;

/**
 * @ClassName: SlidingPaneMenuFragment
 * @Description: TODO
 * @author: raot 719055805@qq.com
 * @date: 2014年9月5日 上午10:42:07
 */
@SuppressLint("NewApi")
public class MenuFragment extends Fragment implements View.OnClickListener,
		Callback, PlatformActionListener {

	private static final String TAG = "MenuFragment";
	private View currentView;
	private TextView tv_login, tv_reg, tv_nickName, tv_logout;
	private ImageView iv_avatar;
	private ACache mCache;
	private Button bt1, bt2, bt3, bt4;
	
	private Platform platform;
	
	private static final int MSG_USERID_FOUND = 1;
	private static final int MSG_LOGIN = 2;
	private static final int MSG_AUTH_CANCEL = 3;
	private static final int MSG_AUTH_ERROR = 4;
	private static final int MSG_AUTH_COMPLETE = 5;
	private static final int SET_USER_INFO = 6;

	public View getCurrentView() {
		return currentView;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "执行onCreateView...");

		mCache = ACache.get(getActivity());
		currentView = inflater.inflate(R.layout.slidingpane_menu_layout,
				container, false);
		bt1 = (Button) currentView.findViewById(R.id.bt1);
		bt2 = (Button) currentView.findViewById(R.id.bt2);
		bt3 = (Button) currentView.findViewById(R.id.bt3);
		bt4 = (Button) currentView.findViewById(R.id.bt4);
		tv_login = (TextView) currentView.findViewById(R.id.tv_login);
		tv_logout = (TextView) currentView.findViewById(R.id.tv_logout);

		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		bt4.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		tv_logout.setOnClickListener(this);

		// 用户信息
		tv_nickName = (TextView) currentView.findViewById(R.id.tv_nickName);
		iv_avatar = (ImageView) currentView.findViewById(R.id.iv_avatar);

		// 初始化登录信息
		mCache = ACache.get(getActivity());
		ShareSDK.initSDK(getActivity());

		System.out.println("onCreateView=" + Thread.currentThread().getName());

		setLoginInfo(null);
		return currentView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_login:
			authorize(new QQ(getActivity()));
			break;
		case R.id.tv_logout:
			Log.i(TAG, "注销用户...");
			platform.removeAccount();
			// 隐藏登录注册按钮
			View ll_login_reg = currentView.findViewById(R.id.ll_login_reg);
			ll_login_reg.setVisibility(View.VISIBLE);
			View view = currentView.findViewById(R.id.ll_user_info);
			view.setVisibility(View.GONE);
			break;

		default:
			break;
		}
		((MainActivity) getActivity()).getSlidingPaneLayout().closePane();
	}

	public void setLoginInfo(Users user) {
		if (user == null) {
			if (mCache.getAsObject("login_info") != null) {
				user = (Users) mCache.getAsObject("login_info");
			}
		}
		Log.i(TAG, "setLoginInfo 显示用户信息...");
		if (user != null && user.getNickName() != ""
				&& user.getNickName() != null) {
			// 隐藏登录注册按钮
			View ll_login_reg = currentView.findViewById(R.id.ll_login_reg);
			ll_login_reg.setVisibility(View.GONE);
			View view = currentView.findViewById(R.id.ll_user_info);
			view.setVisibility(View.VISIBLE);

			// 设置用户信息
			tv_nickName.setText(user.getNickName());

			BitmapUtils bitmapUtils = new BitmapUtils(getActivity());
			// 加载网络图片
			bitmapUtils.display(iv_avatar, user.getAvatar());
		}
	}

	public void onCancel(Platform platform, int action) {

		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
		}
	}

	
	private Users user=null;
	public void onComplete(Platform platform, int action,
			HashMap<String, Object> res) {
		this.platform=platform;
		
		Message message = Message.obtain();
		// 发送腾讯返回过来的信息给服务器保存
		user = new Users();
		user.setNickName(res.get("nickname").toString());
		user.setSex(res.get("gender").toString() == "男" ? 0 : 1);
		user.setAvatar(res.get("figureurl_qq_2").toString().replaceAll(" ", ""));
		System.out.println("onComplete当前线程是:"
				+ Thread.currentThread().getName());
		
		message.what=SET_USER_INFO;
		mCache.put("login_info", user);
		handler.sendMessage(message);

	}

	@Override
	public void onError(Platform platform, int action, Throwable t) {

		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
		}
		t.printStackTrace();
	}

	public void onDestroy() {
		ShareSDK.stopSDK(getActivity());
		super.onDestroy();
	}

	private void login(String plat, String userId,
			HashMap<String, Object> userInfo) {
		Message msg = new Message();
		msg.what = MSG_LOGIN;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	private void authorize(Platform plat) {

		if (plat == null) {
			popupOthers();
			return;
		}

		if (plat.isValid()) {
			String userId = plat.getDb().getUserId();
			if (!TextUtils.isEmpty(userId)) {
				UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
				login(plat.getName(), userId, null);
				return;
			}
		}
		plat.setPlatformActionListener(this);
		plat.SSOSetting(false);
		plat.showUser(null);
	}

	private void popupOthers() {
		Dialog dlg = new Dialog(getActivity());
		View dlgView = View.inflate(getActivity(), R.layout.other_plat_dialog,
				null);
		View tvFacebook = dlgView.findViewById(R.id.tvFacebook);
		tvFacebook.setTag(dlg);
		tvFacebook.setOnClickListener(this);
		View tvTwitter = dlgView.findViewById(R.id.tvTwitter);
		tvTwitter.setTag(dlg);
		tvTwitter.setOnClickListener(this);

		dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dlg.setContentView(dlgView);
		dlg.show();
	}

	public boolean handleMessage(Message msg) {

		switch (msg.what) {
		case MSG_USERID_FOUND: {
			Toast.makeText(getActivity(), R.string.userid_found,
					Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_LOGIN: {

			String text = getString(R.string.logining, msg.obj);
			Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();

		}
			break;
		case MSG_AUTH_CANCEL: {
			Toast.makeText(getActivity(), R.string.auth_cancel,
					Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_AUTH_ERROR: {
			Toast.makeText(getActivity(), R.string.auth_error,
					Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_AUTH_COMPLETE: {
			Toast.makeText(getActivity(), R.string.auth_complete,
					Toast.LENGTH_SHORT).show();
		}
			break;
		}
		return false;
	};

	
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SET_USER_INFO:
				setLoginInfo(user);
				break;

			default:
				break;
			}

		}
	};

}
