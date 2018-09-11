package com.xkx.newssystem.news.service;

import java.util.List;

import com.xkx.newssystem.news.dao.CommentDao;
import com.xkx.newssystem.news.domain.Comment;
import com.xkx.newssystem.news.domain.News;
import com.xkx.newssystem.tools.PageBean;

public class CommentService {
	private CommentDao commentDao = new CommentDao();

	public void deleteComment(Integer commentId) {
		commentDao.deleteComment(commentId);
	}

	public List<Comment> findAllComments() {
		return commentDao.findAllComments();
	}
}
