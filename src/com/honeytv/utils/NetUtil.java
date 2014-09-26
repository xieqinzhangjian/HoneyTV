package com.honeytv.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetUtil {
	
	/**
	 *  检查 是否 连接 2G3G
	 * @param context
	 * @return
	 */
	public static boolean check2G3G(Context context) {
		return checkNet(context, ConnectivityManager.TYPE_MOBILE);
	}

    public static boolean checkNet(Context context) {
        return checkWifi(context) || check2G3G(context);
    }
    
    /**
     * 检查 是否 连接 Wifi
     * @param context
     * @return
     */
    public static boolean checkWifi(Context context) {
        return checkNet(context, ConnectivityManager.TYPE_WIFI);
    }

    public static boolean checkNet(Context context, int type) { // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）

        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getNetworkInfo(type);

                if ((info != null) && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
        }

        return false;
    }
    
    /** 
     * 检测当的网络（WLAN、3G/2G）状态 
     * @param context Context 
     * @return true 表示网络可用 
     */  
    public static boolean isNetworkAvailable(Context context) {  
        ConnectivityManager connectivity = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (connectivity != null) {  
            NetworkInfo info = connectivity.getActiveNetworkInfo();  
            if (info != null && info.isConnected())   
            {  
                // 当前网络是连接的  
                if (info.getState() == NetworkInfo.State.CONNECTED)   
                {  
                    // 当前所连接的网络可用  
                    return true;  
                }  
            }  
        }  
        return false;  
    }  

}
