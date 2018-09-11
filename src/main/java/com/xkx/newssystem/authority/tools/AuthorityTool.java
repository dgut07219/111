package com.xkx.newssystem.authority.tools;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xkx.newssystem.authority.domian.Authority;
import com.xkx.newssystem.user.domain.User;

public class AuthorityTool {
	public static Map<String, Authority> authorityMap = new HashMap<String, Authority>();

	public static String getKey(HttpServletRequest request) {
		String originalUrl = request.getRequestURI();// 获取用户请求的原始网址
		String method = "";
		if (!originalUrl.endsWith("jsp")) {
			method = request.getParameter("method");
		}
		String key = originalUrl + method;
		User user = new User();
		user = (User) request.getSession().getAttribute("session_user");
		if (user == null) {
			key += "anonymous";
		} else {
			key += user.getType();
		}
		return key;

	}
}
