package com.xkx.newssystem.news.service;

import java.util.List;

import com.xkx.newssystem.news.dao.NewsDao;
import com.xkx.newssystem.news.domain.Comment;
import com.xkx.newssystem.news.domain.News;
import com.xkx.newssystem.news.domain.NewsType;
import com.xkx.newssystem.tools.PageBean;
import com.xkx.newssystem.user.dao.UserDao;
import com.xkx.newssystem.user.domain.User;

public class NewsService {
	private NewsDao newsDao = new NewsDao();
	
	/**
	 * 获取全部新闻类型
	 * @return
	 */
	public List<NewsType> findAllNewsType() {
		return newsDao.findAllNewsType();
	}

	/**
	 * 添加新闻
	 * @param news
	 */
	public void addNews(News news) {
		newsDao.addNews(news);
	}

	/**
	 * 获取全部新闻
	 * @param order
	 * @param pc
	 * @param ps
	 * @return
	 */
	public PageBean<News> findAllNews(String order, int pc, int ps) {
		return newsDao.findAllNews(order,pc,ps);
	}

	/**
	 * 获取一条新闻
	 * @param newsId
	 * @return
	 */
	public News getNewsAndComment(Integer newsId,Integer pc ,Integer ps) {
		return newsDao.getNewsAndComment(newsId,pc,ps);
	}

	/**
	 * 增加赞数
	 * @param commentId
	 */
	public void addPraise(Integer commentId) {
		newsDao.addPraise(commentId);
	}

	/**
	 * 添加评论
	 * @param newsId
	 * @param userName
	 */
	public void addComment(Integer newsId, String userName,String content,Integer isReply) {
		newsDao.addComment(newsId,userName,content,isReply);
	}

	/**
	 * 根据Id获取评论
	 * @param commentId
	 * @return
	 */
	public Comment getCommentById(Integer commentId) {
		return newsDao.getCommentById(commentId);
	}

	/**
	 * 删除新闻
	 * @param newsId
	 */
	public void deleteNews(Integer newsId) {
		newsDao.deleteNews(newsId);
	}

	/**
	 * 根据Id查找新闻
	 * @param newsId
	 */
	public News getANews(Integer newsId) {
		return newsDao.getANews(newsId);
	}

	public void editNews(News news) {
		newsDao.edit(news);
	}


	public PageBean<News> findNewsByNewsType(String newsType, int pc, int ps) {
		return newsDao.findNewsByNewsType(newsType,pc,ps);
	}

	public void addNewsType(String name) {
		newsDao.addNewsType(name);
	}
	
}
