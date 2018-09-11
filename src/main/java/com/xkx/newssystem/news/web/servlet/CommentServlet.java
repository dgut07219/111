package com.xkx.newssystem.news.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xkx.newssystem.news.dao.NewsDao;
import com.xkx.newssystem.news.domain.Comment;
import com.xkx.newssystem.news.domain.News;
import com.xkx.newssystem.news.service.CommentService;
import com.xkx.newssystem.news.service.NewsService;
import com.xkx.newssystem.tools.PageBean;

import cn.itcast.servlet.BaseServlet;

public class CommentServlet extends BaseServlet {
	private CommentService commentService = new CommentService();
	
	/**
	 * 删除评论
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取newsId
		String Id = request.getParameter("commentId");
		Integer commentId = Integer.valueOf(Id);

		// 获取用户名
		String userName = request.getParameter("userName");
		
		commentService.deleteComment(commentId);
		
		return new NewsServlet().getNewsAndComment(request, response);
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取newsId
		String Id = request.getParameter("commentId");
		Integer commentId = Integer.valueOf(Id);

		// 获取用户名
		String userName = request.getParameter("userName");
		
		commentService.deleteComment(commentId);
		
		return "f:/jsps/admin/commentList.jsp";
	}
	
	/**
	 * 
	 */
	
	public String findAllComments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Comment> commentList = commentService.findAllComments();
		request.setAttribute("commentList", commentList);
		return "f:/jsps/admin/commentList.jsp";
	}
	
	/**
	 * 获取当前页码
	 * 
	 * @param request
	 * @return
	 */
	public int getPc(HttpServletRequest request) {
		/*
		 * 1.得到pc 如果pc参数不存在，说明pc=1 如果pc参数存在，需要转换为int类型即可
		 */
		String value = request.getParameter("pc");
		if (value == null || value.trim().isEmpty()) {
			return 1;
		}
		return Integer.parseInt(value);
	}
	
	
}
