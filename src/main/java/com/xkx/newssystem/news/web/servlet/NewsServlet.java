package com.xkx.newssystem.news.web.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xkx.newssystem.news.domain.Comment;
import com.xkx.newssystem.news.domain.News;
import com.xkx.newssystem.news.domain.NewsType;
import com.xkx.newssystem.news.service.NewsService;
import com.xkx.newssystem.tools.PageBean;
import com.xkx.newssystem.user.domain.User;

import cn.itcast.servlet.BaseServlet;

public class NewsServlet extends BaseServlet {
	private NewsService newsService = new NewsService();
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		List<NewsType> newsTypes = newsService.findAllNewsType();
		this.getServletContext().setAttribute("newsTypes", newsTypes);
	}
	

	/**
	 * 添加新闻
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		News news = new News();
		// 获取新闻类型
		String newsType = request.getParameter("newsType");
		news.setNewsType(newsType);

		// 获取新闻标题
		String title = request.getParameter("title");
		news.setTitle(title);

		// 获取作者
		String author = request.getParameter("author");
		news.setAuthor(author);

		// 获取时间
		String time = request.getParameter("newsTime");
		// LocalDateTime与String类型转换
		LocalDateTime newsTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		news.setNewsTime(newsTime);

		// 获取ueditor文本内容
		String content = request.getParameter("editorValue");
		news.setContent(content);
		// 添加新闻
		newsService.addNews(news);

		// 保存成功信息
		request.setAttribute("msg", "成功添加新闻!");

		return "f:/jsps/common/news/addNews.jsp";

	}

	public String addNewsType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String name  = request.getParameter("name");
		newsService.addNewsType(name);
		return findAllNewsTypeForManager(request, response);
	}
	
	public String findAllNewsTypeForManager(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 查询所有的新闻类型
		List<NewsType> newsTypes = newsService.findAllNewsType();

		// 保存到request域中
		request.setAttribute("newsTypes", newsTypes);

		return "f:/jsps/admin/newsTypeList.jsp";
	}
	
	/**
	 * 查询所有新闻类型
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllNewsType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 查询所有的新闻类型
		List<NewsType> newsTypes = newsService.findAllNewsType();

		// 保存到request域中
		request.setAttribute("newsTypes", newsTypes);

		return "f:/jsps/common/news/addNews.jsp";
	}

	/**
	 * 查询所有新闻
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 排序指令
		String order = request.getParameter("order");

		// Type
		String type = request.getParameter("type");

		// 获取当前页码
		int pc = getPc(request);

		// 设置一页的记录数
		int ps = 8;

		// 得到PageBean
		PageBean<News> pb = newsService.findAllNews(order, pc, ps);
		request.setAttribute("pb", pb);
		request.setAttribute("order", order);
		if (type == null) {
			return "f:/jsps/common/news/browseNews.jsp";
		} else {
			return "f:/jsps/user/userNews/showNewsForUser.jsp";
		}
	}

	/**
	 * 获取一条新闻和评论
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getNewsAndComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Id = request.getParameter("newsId");
		// 获取页码
		Integer pc = getPc(request);
		Integer ps = 3;
		// 获取id
		Integer newsId = Integer.valueOf(Id);
		News news = newsService.getNewsAndComment(newsId, pc, ps);

		request.setAttribute("news", news);
		return "f:/jsps/common/news/showNewsDetail.jsp";
	}

	/**
	 * 增加赞数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPraise(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取commentId
		String Id = request.getParameter("commentId");
		Integer commentId = Integer.valueOf(Id);

		// 增加赞数
		newsService.addPraise(commentId);

		return getNewsAndComment(request, response);
	}

	/**
	 * 添加评论
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取newsId
		String Id = request.getParameter("newsId");
		Integer newsId = Integer.valueOf(Id);

		// 获取用户名
		String userName = request.getParameter("userName");

		// 获取内容
		String content = request.getParameter("content");
		System.out.println(content);

		String isReply = request.getParameter("isReply");

		// 被回复的评论的用户
		String userName1 = request.getParameter("userName1");

		// 被回复的楼层
		String stair = request.getParameter("stair");

		if (isReply.equals("1")) {
			content = "回复" + "第" + stair + "楼层" + userName1 + "的评论:&nbsp;&nbsp" + content;
		}

		newsService.addComment(newsId, userName, content, Integer.valueOf(isReply));

		return getNewsAndComment(request, response);
	}

	/**
	 * 添加评论之前的方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */

	public String addReplyPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取newsId
		String Id = request.getParameter("newsId");
		Integer newsId = Integer.valueOf(Id);

		// 获取pc
		String pc = request.getParameter("pc");

		// 获取回复的评论
		Comment comment = getCommentById(request, response);
		request.setAttribute("pc", pc);
		request.setAttribute("newsId", newsId);
		request.setAttribute("comment", comment);
		return "f:/jsps/common/comment/addReply.jsp";
	}

	/**
	 * 管理员管理新闻
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String manageNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		findAllNews(request, response);
		return "f:/jsps/admin/manage/manageNews.jsp";
	}

	/**
	 * 作者管理新闻
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String authorManageNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		findAllNews(request, response);

		return "f:/jsps/newsAuthor/manage/editNewsPre.jsp";
	}

	/**
	 * 编辑新闻前的方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editNewsPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取newsId
		String Id = request.getParameter("newsId");
		Integer newsId = Integer.valueOf(Id);

		// 根据Id查找news
		News news = newsService.getANews(newsId);
		// 获取pc
		String pc = request.getParameter("pc");

		// 排序指令
		String order = request.getParameter("order");

		// 查询所有新闻类型
		findAllNewsType(request, response);
		request.setAttribute("news", news);
		request.setAttribute("pc", pc);
		request.setAttribute("order", order);
		return "f:/jsps/newsAuthor/manage/editNews.jsp";
	}

	/**
	 * 修改新闻
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Id = request.getParameter("newsId");
		Integer newsId = Integer.valueOf(Id);

		News news = new News();
		// 获取新闻类型
		String newsType = request.getParameter("newsType");
		news.setNewsType(newsType);

		// 获取新闻标题
		String title = request.getParameter("title");
		news.setTitle(title);

		// 获取作者
		String author = request.getParameter("author");
		news.setAuthor(author);

		// 获取时间
		String time = request.getParameter("newsTime");
		// LocalDateTime与String类型转换
		LocalDateTime newsTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		news.setNewsTime(newsTime);

		// 获取ueditor文本内容
		String content = request.getParameter("editorValue");
		news.setContent(content);

		news.setNewsId(newsId);
		newsService.editNews(news);

		findAllNews(request, response);
		return "f:/jsps/newsAuthor/manage/editNewsPre.jsp";
	}

	/**
	 * 删除新闻
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Id = request.getParameter("newsId");
		Integer newsId = Integer.valueOf(Id);
		newsService.deleteNews(newsId);

		findAllNews(request, response);

		return "f:/jsps/admin/manage/manageNews.jsp";
	}

	public String findNewsByNewsType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newsType = request.getParameter("name");
		// 获取当前页码
		int pc = getPc(request);

		// 设置一页的记录数
		int ps = 3;
		// 得到PageBean
		PageBean<News> pb = newsService.findNewsByNewsType(newsType, pc, ps);
		request.setAttribute("pb", pb);
		request.setAttribute("newsType", newsType);
		return "f:/jsps/common/news/browseNewsByType.jsp";
	}

	/**
	 * 根据Id获取评论
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public Comment getCommentById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取commentId
		String Id = request.getParameter("commentId");
		Integer commentId = Integer.valueOf(Id);

		return newsService.getCommentById(commentId);
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
		if (value == null || value.trim().isEmpty() || Integer.parseInt(value) == 0) {
			return 1;
		}
		return Integer.parseInt(value);
	}

}
