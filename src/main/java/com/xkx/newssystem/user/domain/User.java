package com.xkx.newssystem.user.domain;

import java.sql.Timestamp;

public class User {
	private Integer userId;
	private String name;
	private String type;
	private String headIconUrl; // 头像路径
	private String password;
	private Timestamp registerDate;
	private String enable;

	public Integer getUserId() {
		return userId;
	}

	public String getHeadIconUrl() {
		return headIconUrl;
	}

	public void setHeadIconUrl(String headIconUrl) {
		this.headIconUrl = headIconUrl;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", type=" + type + ", headIconUrl=" + headIconUrl
				+ ", password=" + password + ", registerDate=" + registerDate + ", enable=" + enable + "]";
	}

	
}
