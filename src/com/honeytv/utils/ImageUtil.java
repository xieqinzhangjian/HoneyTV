package com.honeytv.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class ImageUtil {

	private final static String TAG = "ImageUtil";
	private Context mContext;
	private String SDCARD_PATH;
	// 软引用
	private HashMap<Object, SoftReference<Drawable>> imageCache;
	
	public ImageUtil(Context mContext) {
		super();
		imageCache = new HashMap<Object, SoftReference<Drawable>>();
		this.mContext = mContext;
	}
	
	public ImageUtil() {
		SDCARD_PATH = Environment.getExternalStorageDirectory() + "/";
	}
	
	public File creatSDDirectory(File dirName) {
		File dir = new File(dirName, SDCARD_PATH);
		dir.mkdirs();
		return dir;
	}
	
	public File creatSDFile(File path, String fileName) throws IOException {
		File file = new File(path, fileName);
		file.createNewFile();
		return file;
	}
	
	public static String getAlbumDirectory() {
		File dir = new File(Environment.getExternalStorageDirectory(),
				Constants.APP_HOME_PATH + "/" + Constants.IMAGE_PATH);
		if (dir != null) {
			if (!dir.mkdirs()) {
				if (!dir.exists()) {
					Log.e(TAG, "目录创建失败：" + dir.getPath());
					return null;
				}
			}
		}
		return dir.getAbsolutePath();
	}
	
	
	public boolean write2SD(String fileName, InputStream inputStream) {
		File file = null;
		OutputStream output = null;
		File path = new File(Environment.getExternalStorageDirectory(), fileName);
		try {
			creatSDDirectory(path);
			file = creatSDFile(path, fileName);
			output = new FileOutputStream(file);

			byte[] buffer = new byte[4 * 1024];

			int len = 0;
			len = inputStream.read(buffer);
			while (len != -1) {
				output.write(buffer, 0, len);
				len = inputStream.read(buffer);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "往SD卡上写文件失败：dir:" + path + ", file:" + fileName
					+ "] :" + e.getMessage());
			return false;
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				Log.e(TAG, "关闭SD卡上的文件出错：" + e.getMessage());
				return false;
			}
		}
		return true;
	}
	
    
    // 将InputStream转换成Bitmap  
    public static Bitmap inputStream2Bitmap(InputStream is) {  
        return BitmapFactory.decodeStream(is);  
    }  
    
    /**
     * 将图片路径转Bitmap
     * @param filePach
     * @return
     */
    public static Bitmap filePach2Bitmap(String filePach){
    	File file = new File(filePach);
    	FileInputStream inputStream=null;
    	Bitmap bitmap = null;
    	
    	try {
    		inputStream = new FileInputStream(file);
    		bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return bitmap;
    }
	
}
