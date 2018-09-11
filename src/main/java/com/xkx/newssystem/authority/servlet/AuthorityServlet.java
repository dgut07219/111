package com.xkx.newssystem.authority.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xkx.newssystem.authority.domian.Authority;
import com.xkx.newssystem.authority.service.AuthorityService;
import com.xkx.newssystem.authority.tools.AuthorityTool;

public class AuthorityServlet extends HttpServlet {
	/**
	 * 加载权限
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		AuthorityService authorityService = new AuthorityService();
		List<Authority> authorityList = authorityService.getAllAuthorities();
		String key;
		for (Authority authority : authorityList) {
			if(authority.getMethod() == null || authority.getMethod().trim().isEmpty()) {
				key = authority.getUrl()+authority.getUserType();
			}
			else {
				key = authority.getUrl()+authority.getMethod()+authority.getUserType();
			}
			AuthorityTool.authorityMap.put(key, authority);
		}
	}

}
