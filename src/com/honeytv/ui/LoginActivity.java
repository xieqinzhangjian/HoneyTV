package com.honeytv.ui;

import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

import com.honeytv.entity.Users;
import com.honeytv.utils.ACache;

public class LoginActivity extends Activity implements Callback, OnClickListener, PlatformActionListener {
	private static final String TAG = "LoginActivity";
	private Context mContext;
	private String filePath;
	private ACache mCache;
	private static final int MSG_USERID_FOUND = 1;
	private static final int MSG_LOGIN = 2;
	private static final int MSG_AUTH_CANCEL = 3;
	private static final int MSG_AUTH_ERROR = 4;
	private static final int MSG_AUTH_COMPLETE = 5;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mCache = ACache.get(this);
		ShareSDK.initSDK(this);
		mContext = this;
		setContentView(R.layout.login);
		findViewById(R.id.iv_qq_login).setOnClickListener(this);
		findViewById(R.id.iv_sina_login).setOnClickListener(this);
		findViewById(R.id.bt_user_login).setOnClickListener(this);
	}

	protected void onDestroy() {
		ShareSDK.stopSDK(this);
		super.onDestroy();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_sina_login: {
			authorize(new SinaWeibo(this));
		}
			break;
		case R.id.iv_qq_login: {
			authorize(new QQ(this));
		}
			break;
		case R.id.bt_user_login: {
			// 普通登录
		}
		}
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

		Dialog dlg = new Dialog(this);
		View dlgView = View.inflate(this, R.layout.other_plat_dialog, null);
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

	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
		this.finish();
		// 发送腾讯返回过来的信息给服务器保存

		Users user = new Users(); 
		user.setNickName(res.get("nickname").toString());
		user.setSex(res.get("gender").toString() == "男" ? 0 : 1);
		user.setAvatar(res.get("figureurl_qq_2").toString().replaceAll(" ", ""));
		mCache.put("login_info", user);
	
	}


	public void onError(Platform platform, int action, Throwable t) {
 
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
		}
		t.printStackTrace();
	}

	public void onCancel(Platform platform, int action) {

		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
		}
	}

	private void login(String plat, String userId, HashMap<String, Object> userInfo) {

		Message msg = new Message();
		msg.what = MSG_LOGIN;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	public boolean handleMessage(Message msg) {

		switch (msg.what) {
		case MSG_USERID_FOUND: {
			Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_LOGIN: {

			String text = getString(R.string.logining, msg.obj);
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

		}
			break;
		case MSG_AUTH_CANCEL: {
			Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_AUTH_ERROR: {
			Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_AUTH_COMPLETE: {
			Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
		}
			break;
		}
		return false;
	}

}
