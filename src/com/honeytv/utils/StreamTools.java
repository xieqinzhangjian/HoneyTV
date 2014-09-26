package com.honeytv.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Environment;

public class StreamTools {

	/**
	 * 把流转换为String类型
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readFromStream(InputStream is) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = is.read(buffer))!=-1){
			baos.write(buffer, 0, len);
		}
		is.close();
		String result = baos.toString();
		baos.close();
		return result;
	}
	
	 // 将InputStream转换成byte[]  
    public byte[] InputStream2Bytes(InputStream is) {  
        String str = "";  
        byte[] readByte = new byte[1024];  
        int readCount = -1;  
        try {  
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {  
                str += new String(readByte).trim();  
            }  
            return str.getBytes();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  

    /**
     * 检测sd卡是否可用（包含是否挂载和是否可读写） 
     * 
     */
    public static boolean checkSDState() {
    	return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
  
}
