package com.xkx.newssystem.news.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.xkx.newssystem.tools.PageBean;
import com.xkx.newssystem.user.domain.User;

public class News {
	private Integer newsId;
	private String newsType;
	private String author;
	private String title;
	private String content;
	private LocalDateTime newsTime;
	private Timestamp publishTime;
	private String check;
	private PageBean<Comment> pb;
	private User user;
	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getNewsTime() {
		return newsTime;
	}

	public void setNewsTime(LocalDateTime newsTime) {
		this.newsTime = newsTime;
	}

	
	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime2) {
		this.publishTime = publishTime2;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	@Override
	public String toString() {
		return "News [newsId=" + newsId + ", newsType=" + newsType + ", author=" + author + ", title=" + title
				+ ", content=" + content + ", newsTime=" + newsTime + ", publishTime=" + publishTime + ", check="
				+ check + ", pb=" + pb + "]";
	}

	public PageBean<Comment> getPb() {
		return pb;
	}

	public void setPb(PageBean<Comment> pb) {
		this.pb = pb;
	}

}
