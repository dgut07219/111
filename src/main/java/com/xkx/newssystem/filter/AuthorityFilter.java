package com.xkx.newssystem.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xkx.newssystem.authority.domian.Authority;
import com.xkx.newssystem.authority.tools.AuthorityTool;
import com.xkx.newssystem.authority.tools.Message;

@WebFilter(filterName = "AuthorityFilter", urlPatterns = { "/UserServlet", "/NewsServlet", "/CommonServlet","/jsps/admin/*",
		"/jsps/user/*", "/jsps/newsAuthor/*", "/jsps/common/*" })
public class AuthorityFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		String key = AuthorityTool.getKey(req);
		System.out.println("filter=" + key);
		Authority authority = AuthorityTool.authorityMap.get(key);

		if (authority == null) {// 无权限
			Message message = new Message();
			message.setMessage("权限不够！");
			message.setRedirectTime(1000);
			request.setAttribute("message", message);
			request.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		} else
			chain.doFilter(request, response);// 有权限，可以继续访问
	}

	public void destroy() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
