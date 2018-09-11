package com.xkx.newssystem.news.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.xkx.newssystem.news.domain.Comment;
import com.xkx.newssystem.news.domain.News;
import com.xkx.newssystem.tools.PageBean;
import com.xkx.newssystem.user.domain.User;

import cn.itcast.jdbc.TxQueryRunner;

public class CommentDao {
	private QueryRunner qr = new TxQueryRunner();

	public void deleteComment(Integer commentId) {
		try {
			String sql = "delete from comment where commentId = ?";
			qr.update(sql, commentId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Comment> findAllComments() {
		try {
			String sql = "SELECT * FROM comment";
			return qr.query(sql, new BeanListHandler<Comment>(Comment.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
