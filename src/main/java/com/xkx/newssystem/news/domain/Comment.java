package com.xkx.newssystem.news.domain;

import java.sql.Timestamp;

public class Comment {
	private Integer commentId;
	private Integer newsId; // 新闻Id
	private String userName; // 用户名
	private String content; // 评论内容
	private Timestamp time;
	private Integer praise; // 赞数
	public String getHeadIconUrl() {
		return headIconUrl;
	}


	public void setHeadIconUrl(String headIconUrl) {
		this.headIconUrl = headIconUrl;
	}


	private Integer stair;// 楼层
	private Integer isReply;
	private String headIconUrl;
	public Integer getCommentId() {
		return commentId;
	}


	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}


	public Integer getNewsId() {
		return newsId;
	}


	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Timestamp getTime() {
		return time;
	}


	public void setTime(Timestamp time) {
		this.time = time;
	}


	public Integer getPraise() {
		return praise;
	}


	public void setPraise(Integer praise) {
		this.praise = praise;
	}


	public Integer getStair() {
		return stair;
	}


	public void setStair(Integer stair) {
		this.stair = stair;
	}

	

	public Integer getIsReply() {
		return isReply;
	}


	public void setIsReply(Integer isReply) {
		this.isReply = isReply;
	}


	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", newsId=" + newsId + ", userName=" + userName + ", content="
				+ content + ", time=" + time + ", praise=" + praise + ", stair=" + stair + "]";
	}

}
