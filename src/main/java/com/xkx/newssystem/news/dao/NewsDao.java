package com.xkx.newssystem.news.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.xkx.newssystem.news.domain.Comment;
import com.xkx.newssystem.news.domain.News;
import com.xkx.newssystem.news.domain.NewsType;
import com.xkx.newssystem.tools.PageBean;
import com.xkx.newssystem.user.dao.UserDao;
import com.xkx.newssystem.user.domain.User;

import cn.itcast.jdbc.TxQueryRunner;

public class NewsDao {
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 查询所有的新闻类型
	 * 
	 * @return
	 */
	public List<NewsType> findAllNewsType() {
		try {
			String sql = "select * from NewsType";
			return qr.query(sql, new BeanListHandler<NewsType>(NewsType.class));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加新闻
	 * 
	 * @param news
	 */
	public void addNews(News news) {
		try {
			String sql = "INSERT INTO NEWS(newsType,author,title,content,newsTime) VALUES(?,?,?,?,?)";
			Object[] params = { news.getNewsType(), news.getAuthor(), news.getTitle(), news.getContent(),
					news.getNewsTime() };
			qr.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找所有新闻
	 * 
	 * @param order
	 * @param pc
	 * @param ps
	 * @throws SQLException
	 */
	public PageBean<News> findAllNews(String order, int pc, int ps) {
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/news";
		String username = "root";
		String password = "123";

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driverClassName);
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			// 创建PageBean
			PageBean pb = new PageBean();
			List<News> beanList = new ArrayList<News>();
			News news = new News();
			pb.setPc(pc);
			pb.setPs(ps);

			// 查询总记录数tr
			String tr_sql = "SELECT COUNT(*) FROM news ";
			Number number = (Number) qr.query(tr_sql, new ScalarHandler());
			int tr = number.intValue();
			pb.setTr(tr);

			// 得到的结果放在ResultSet
			if (order == null || order.equals("desc")) {
				String sql_all = "SELECT * FROM news";

				PreparedStatement pstmt = con.prepareStatement(sql_all);
				// 得到的结果放在ResultSet
				rs = pstmt.executeQuery();

				List<News> newsList = getNewsList(rs);

				for (int i = tr - 1 - (pc - 1) * ps; i > tr - 1 - (pc - 1) * ps - ps; i--) {
					if (i >= 0) {
						beanList.add(newsList.get(i));
					}
				}
			} else {
				// 得到beanList
				// sql语句
				String sql = "SELECT * FROM news LIMIT ?,?";
				PreparedStatement pstmt = con.prepareStatement(sql);

				/*
				 * 为参数赋值
				 */
				pstmt.setInt(1, ps * (pc - 1));
				pstmt.setInt(2, ps);

				// 得到的结果放在ResultSet
				rs = pstmt.executeQuery();
				beanList = getNewsList(rs);
			}

			pb.setBeanList(beanList);
			return pb;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取一条新闻
	 * 
	 * @param newsId
	 * @return
	 */
	public News getNewsAndComment(Integer newsId, Integer pc, Integer ps) {
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/news";
		String username = "root";
		String password = "123";

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driverClassName);
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			// 得到的结果放在ResultSet
			String sql = "SELECT * FROM news WHERE newsId = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newsId);

			// 得到的结果放在ResultSet
			rs = pstmt.executeQuery();
			News news = getNewsList(rs).get(0);

			// 创建PageBean
			PageBean<Comment> pb = new PageBean<Comment>();
			pb.setPc(pc);
			pb.setPs(ps);

			// 查询总记录数tr
			String tr_sql = "SELECT COUNT(*) FROM comment WHERE newsId = ?";
			Number number = (Number) qr.query(tr_sql, new ScalarHandler(), newsId);
			int tr = number.intValue();
			pb.setTr(tr);

			// 得到beanList
			// sql语句
			String sql_comment = "SELECT * FROM comment WHERE newsId = ?  ORDER BY time desc LIMIT ?,?";
			Object[] params = { newsId, ps * (pc - 1), ps };
			List<Comment> beanList = new ArrayList<Comment>();
			beanList = qr.query(sql_comment, new BeanListHandler<Comment>(Comment.class), params);
			pb.setBeanList(beanList);

			news.setPb(pb);
			return news;

		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	/**
	 * 增加赞数
	 * 
	 * @param commentId
	 */
	public void addPraise(Integer commentId) {
		try {
			String sql = "UPDATE comment set praise = praise+1 where commentId = ?";
			qr.update(sql, commentId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加评论
	 * 
	 * @param newsId
	 * @param userName
	 * @param content
	 */
	public void addComment(Integer newsId, String userName, String content, Integer isReply) {
		try {
			String sql_count = "SELECT COUNT(*) FROM comment where newsId = ?";
			Number number = (Number) qr.query(sql_count, new ScalarHandler(), newsId);

			// 评论数
			int commentCount = number.intValue();
			
			//头像路径
			User user = new UserDao().findByUsername(userName);
			
			String sql = "INSERT INTO Comment(newsId,userName,content,stair,isReply,headIconUrl) VALUES(?,?,?,?,?,?)";
			Object[] params = { newsId, userName, content, commentCount + 1, isReply,user.getHeadIconUrl()};
			qr.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据Id获取评论
	 * 
	 * @param commentId
	 * @return
	 */
	public Comment getCommentById(Integer commentId) {
		try {
			String sql = "SELECT * FROM comment where commentId = ?";
			return qr.query(sql, new BeanHandler<Comment>(Comment.class), commentId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除新闻
	 * 
	 * @param newsId
	 */
	public void deleteNews(Integer newsId) {
		try {
			String sql = "DELETE FROM news where newsId = ?";
			qr.update(sql, newsId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据id查新闻
	 * 
	 * @param newsId
	 */
	public News getANews(Integer newsId) {
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/news";
		String username = "root";
		String password = "123";

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driverClassName);
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			// 得到的结果放在ResultSet
			String sql = "SELECT * FROM news WHERE newsId = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newsId);

			// 得到的结果放在ResultSet
			rs = pstmt.executeQuery();
			return getNewsList(rs).get(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 遍历rs
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<News> getNewsList(ResultSet rs) throws SQLException {
		List<News> newsList = new ArrayList<News>();
		while (rs.next()) // 移动到下一行并判断下一行是否存在
		{
			News news = new News();
			Integer newsId = rs.getInt("newsId");
			String newsType = rs.getString("newsType");
			String author = rs.getString("author");
			String title = rs.getString("title");
			String content = rs.getString("content");
			LocalDateTime newsTime = rs.getTimestamp("newsTime").toLocalDateTime();
			Timestamp publishTime = rs.getTimestamp("publishTime");
			String check = rs.getString("check");

			// 设置beanList
			news.setNewsId(newsId);
			news.setNewsType(newsType);
			news.setAuthor(author);
			news.setTitle(title);
			news.setContent(content);
			news.setNewsTime(newsTime);
			news.setPublishTime(publishTime);
			news.setCheck(check);
			newsList.add(news);
		}
		return newsList;
	}

	public void edit(News news) {
		try {
			String sql = "UPDATE NEWS SET newsType = ?,author = ?,title = ?,content = ?,newsTime = ? WHERE newsId = ?";
			Object[] params = { news.getNewsType(), news.getAuthor(), news.getTitle(), news.getContent(),
					news.getNewsTime(), news.getNewsId() };
			qr.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据新闻分类查找新闻
	 * 
	 * @param newsType
	 * @param pc
	 * @param ps
	 * @return
	 */

	public PageBean<News> findNewsByNewsType(String newsType, int pc, int ps) {
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/news";
		String username = "root";
		String password = "123";

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driverClassName);
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			// 创建PageBean
			PageBean pb = new PageBean();
			List<News> beanList = new ArrayList<News>();
			News news = new News();
			pb.setPc(pc);
			pb.setPs(ps);

			// 查询总记录数tr
			String tr_sql = "SELECT COUNT(*) FROM news WHERE newsType = ?";
			Number number = (Number) qr.query(tr_sql, new ScalarHandler(),newsType);
			int tr = number.intValue();
			pb.setTr(tr);

			// 得到beanList
			// sql语句
			String sql = "SELECT * FROM news WHERE newsType = ? LIMIT ?,?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			/*
			 * 为参数赋值
			 */
			pstmt.setString(1, newsType);
			pstmt.setInt(2, ps * (pc - 1));
			pstmt.setInt(3, ps);

			// 得到的结果放在ResultSet
			rs = pstmt.executeQuery();
			beanList = getNewsList(rs);
			pb.setBeanList(beanList);
			return pb;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	public void addNewsType(String name) {
		try {
			String sql = "insert into newsType(name) values(?)";
			qr.update(sql, name);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
