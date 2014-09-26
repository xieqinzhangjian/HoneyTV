package com.honeytv.entity;

import java.io.Serializable;
import java.util.Date;

import android.graphics.Bitmap;

/**
 * @description 用户
 * @author tianshaokai
 * @date 2014-09-04 13:52:22
 * @email 113869kai@sina.com
 * @version 1.0
 */

public class Users implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String _id;
	private String loginName;
	private String password;
	private String email;
	private String url;
	private String profileImageUrl;
	private String location;
	private String signature;
	private String profile;
	private int code;
	private String avatar;
	private String type;
	private String user_id;
	private boolean isBlock;
	private int userType;
	private int score;
	private int topicCount;
	private int replyCount;
	private int followerCount;
	private int followingCount;
	private int collectGameCount;
	private int collectTopicCount;
	private int praiseTopicCount;
	private int praiseGameCount;
	private int praiseFriendMessageCount;
	private int friendMessageCount;
	private Date create_at;
	private Date update_at;
	private boolean isStar;
	private String level;
	private boolean active;
	private boolean receiveReplyMail;
	private boolean receiveAtMail;
	private String lastIp;
	private int loginType;
	private String nickName;
	private int sex;
	private String source;
	private String openid;
	private String mobile;
	private String background_img_url;
	private int isV;
	private int isBind;
	private String clubId;
	private String clubName;
	private int clubCount;
	private Bitmap avatarBm;
	/**
	 * 收藏的游戏
	 */
	private int collect_game_count; 
	/**
	 * 粉丝的数量
	 */
	private int follower_count; 
	/**
	 * 关注的人数
	 */
	private int following_count; 
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public boolean isBlock() {
		return isBlock;
	}
	public void setBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getTopicCount() {
		return topicCount;
	}
	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public int getFollowerCount() {
		return followerCount;
	}
	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}
	public int getFollowingCount() {
		return followingCount;
	}
	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}
	public int getCollectGameCount() {
		return collectGameCount;
	}
	public void setCollectGameCount(int collectGameCount) {
		this.collectGameCount = collectGameCount;
	}
	public int getCollectTopicCount() {
		return collectTopicCount;
	}
	public void setCollectTopicCount(int collectTopicCount) {
		this.collectTopicCount = collectTopicCount;
	}
	public int getPraiseTopicCount() {
		return praiseTopicCount;
	}
	public void setPraiseTopicCount(int praiseTopicCount) {
		this.praiseTopicCount = praiseTopicCount;
	}
	public int getPraiseGameCount() {
		return praiseGameCount;
	}
	public void setPraiseGameCount(int praiseGameCount) {
		this.praiseGameCount = praiseGameCount;
	}
	public int getPraiseFriendMessageCount() {
		return praiseFriendMessageCount;
	}
	public void setPraiseFriendMessageCount(int praiseFriendMessageCount) {
		this.praiseFriendMessageCount = praiseFriendMessageCount;
	}
	public int getFriendMessageCount() {
		return friendMessageCount;
	}
	public void setFriendMessageCount(int friendMessageCount) {
		this.friendMessageCount = friendMessageCount;
	}
	public Date getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}
	public Date getUpdate_at() {
		return update_at;
	}
	public void setUpdate_at(Date update_at) {
		this.update_at = update_at;
	}
	public boolean isStar() {
		return isStar;
	}
	public void setStar(boolean isStar) {
		this.isStar = isStar;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isReceiveReplyMail() {
		return receiveReplyMail;
	}
	public void setReceiveReplyMail(boolean receiveReplyMail) {
		this.receiveReplyMail = receiveReplyMail;
	}
	public boolean isReceiveAtMail() {
		return receiveAtMail;
	}
	public void setReceiveAtMail(boolean receiveAtMail) {
		this.receiveAtMail = receiveAtMail;
	}
	public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	public int getLoginType() {
		return loginType;
	}
	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBackground_img_url() {
		return background_img_url;
	}
	public void setBackground_img_url(String background_img_url) {
		this.background_img_url = background_img_url;
	}
	public int getIsV() {
		return isV;
	}
	public void setIsV(int isV) {
		this.isV = isV;
	}
	public int getIsBind() {
		return isBind;
	}
	public void setIsBind(int isBind) {
		this.isBind = isBind;
	}
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public int getClubCount() {
		return clubCount;
	}
	public void setClubCount(int clubCount) {
		this.clubCount = clubCount;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public int getCollect_game_count() {
		return collect_game_count;
	}
	public void setCollect_game_count(int collect_game_count) {
		this.collect_game_count = collect_game_count;
	}
	public int getFollower_count() {
		return follower_count;
	}
	public void setFollower_count(int follower_count) {
		this.follower_count = follower_count;
	}
	public int getFollowing_count() {
		return following_count;
	}
	public void setFollowing_count(int following_count) {
		this.following_count = following_count;
	}
	public Bitmap getAvatarBm() {
		return avatarBm;
	}
	public void setAvatarBm(Bitmap avatarBm) {
		this.avatarBm = avatarBm;
	}

	
}
