package com.honeytv.entity;

import java.io.Serializable;
import java.util.List;


public class VideoList implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int code,totalPage,currentPage;
	 public String message;
	 public List<VideoInfo> videoList;
	 
	 public static class VideoInfo implements Serializable{
		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Users user;
		 public String video_url;//视频地址
		 public String video_time_length;//视频时长
		 public String author_id;//视频作者id
		 public String title; //视频名称
		 public String _id; //视频id
		 public long visit_count; //视频浏览次数
		 
	 }

}
