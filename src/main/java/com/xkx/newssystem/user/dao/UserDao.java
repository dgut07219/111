package com.xkx.newssystem.user.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.xkx.newssystem.tools.PageBean;
import com.xkx.newssystem.user.domain.User;
import com.xkx.newssystem.user.domain.UserInformation;

import cn.itcast.jdbc.TxQueryRunner;

/**
 * User持久层
 * 
 * @author XuKexiang
 *
 */
public class UserDao {
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 按用户名查找用户
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		try {
			String sql = "SELECT * FROM USER WHERE NAME=?";
			return qr.query(sql, new BeanHandler<User>(User.class), username);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		try {
			// sql语句
			String sql = "INSERT INTO USER(NAME,PASSWORD,headIconUrl,type) VALUES(?,?,?,?)";
			// 参数
			Object[] params = { user.getName(), user.getPassword(), "head_img/0.jpg" ,user.getType() };
			// 执行sql
			qr.update(sql, params);
			
			Number number = (Number) qr.query("select userId from user where name = ?", new ScalarHandler(),user.getName());
			Integer userId = number.intValue();
			
			//插入用户信息
			String sql_information = "INSERT INTO userinformation(userId) values(?)";
			qr.update(sql_information,userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找单页所用用户
	 * 
	 * @return
	 */
	public PageBean<User> findAllUser(String order, int pc, int ps) {
		try {
			PageBean pb = new PageBean();
			pb.setPc(pc);
			pb.setPs(ps);

			// 查询总记录数tr
			String tr_sql = "SELECT COUNT(*) FROM user ";
			Number number = (Number) qr.query(tr_sql, new ScalarHandler());
			int tr = number.intValue();
			pb.setTr(tr);

			// 得到beanList
			// sql语句
			String sql = "SELECT * FROM user LIMIT ?,?";
			Object[] params = { ps * (pc - 1), ps };
			List<User> beanList = new ArrayList<User>();
			if (order.equals("desc")) {
				String sql_all = "SELECT * FROM user";

				// 全部的用户
				List<User> userList = qr.query(sql_all, new BeanListHandler<User>(User.class));
				for (int i = tr - 1 - (pc - 1) * ps; i > tr - 1 - (pc - 1) * ps - ps; i--) {
					if (i >= 0) {
						beanList.add(userList.get(i));
					}
				}
			} else if (order.equals("asc")) {
				beanList = qr.query(sql, new BeanListHandler<User>(User.class), params);
			}
			pb.setBeanList(beanList);

			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 更改用户账号的状态
	 * 
	 * @param user
	 */
	public void changeEnable(String userId, String enable) {
		try {
			String sql_1 = "UPDATE USER SET ENABLE='use' WHERE userId=?";
			String sql_2 = "UPDATE USER SET ENABLE='stop' WHERE userId=?";

			// 若之前账号状态为stop
			if (enable != null) {
				if (enable.equals("stop")) {
					qr.update(sql_1, userId);
				} else if (enable.equals("use")) {
					qr.update(sql_2, userId);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 多重条件查询
	 * 
	 * @param criteria
	 * @param pc
	 * @param ps
	 * @param lowDate
	 * @param upDate
	 * @return
	 */
	public PageBean<User> searchUser(User criteria, int pc, int ps, String lowDate, String upDate, String order) {
		try {
			// 创建PageBean对象
			PageBean pb = new PageBean();
			pb.setPc(pc);
			pb.setPs(ps);

			// 查询记录数SELECT语句
			StringBuilder selectSql = new StringBuilder("select count(*) from user");

			// WHERE子句
			StringBuilder whereSql = new StringBuilder(" WHERE 1=1");

			// 查询参数的集合
			List<Object> params = new ArrayList<Object>();

			// 用户类型
			String type = criteria.getType();
			if (!type.equals("all") || type != null && type.trim().isEmpty()) {
				whereSql.append(" and type = ?");
				params.add(type);
			}
			// 用户名模糊查询
			String name = criteria.getName();
			if (name != null && !name.trim().isEmpty()) {
				whereSql.append(" and name LIKE ?");
				params.add("%" + name + "%");
			}
			// 账号状态
			String enable = criteria.getEnable();
			if (!enable.equals("all") || enable != null && enable.trim().isEmpty()) {
				whereSql.append(" and enable = ?");
				params.add(enable);
			}

			// 日期>= lowDate
			if (lowDate != null && !lowDate.trim().isEmpty()) {
				whereSql.append(" and registerDate >= ?");
				params.add(lowDate);
			}

			// 日期 < upDate
			if (upDate != null && !upDate.trim().isEmpty()) {
				whereSql.append(" and registerDate < ?");
				params.add(upDate);
			}

			// 查询记录数
			Number number = (Number) qr.query(selectSql.append(whereSql).toString(), new ScalarHandler(),
					params.toArray());
			int tr = number.intValue();

			// 设置记录数
			pb.setTr(tr);

			/*
			 * 得到BeanList
			 */

			List<User> beanList = new ArrayList<User>();

			// 查询用户的SQL语句
			StringBuilder sql = new StringBuilder("SELECT * FROM user");
			StringBuilder limitSql = new StringBuilder(" limit ?,?");

			// 按照用户Id降序
			if (order.equals("desc")) {

				// 全部的用户
				List<User> userList = qr.query(sql.append(whereSql).toString(), new BeanListHandler<User>(User.class),
						params.toArray());
				for (int i = tr - 1 - (pc - 1) * ps; i > tr - 1 - (pc - 1) * ps - ps; i--) {
					if (i >= 0) {
						beanList.add(userList.get(i));
					}
				}
			} else if (order.equals("asc") || order == null) { // 升序，默认为升序
				params.add((pc - 1) * ps);
				params.add(ps);
				beanList = qr.query(sql.append(whereSql).append(limitSql).toString(),
						new BeanListHandler<User>(User.class), params.toArray());
			}
			// 设置beanList
			pb.setBeanList(beanList);

			// 返回PageBean
			return pb;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 */
	public void deleteUser(String userId) {
		try {
			String sql = "DELETE FROM USER WHERE userId = ?";
			qr.update(sql, userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(String userId, String newPassword) {
		try {
			String sql = "UPDATE USER SET password = ? WHERE userId = ?";
			Object[] params = { newPassword, userId };
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取用户个人信息
	 * @param userId
	 * @return
	 */
	public UserInformation getInformation(String userId) {
		try {
			String sql = "SELECT * FROM userInformation where userId = ?";
			return qr.query(sql, new BeanHandler<UserInformation>(UserInformation.class),userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void modifyInformation(UserInformation userInformation, String userId, String headImg_path) {
		try {
			//修改个人信息
			String sql = "UPDATE userInformation SET sex = ? , hobby = ? where userInformationId = ?";
			Object[] params = {userInformation.getSex(),userInformation.getHobby(),userInformation.getUserInformationId()};
			qr.update(sql, params);
			
			//修改头像
			String sql_headImg = "UPDATE user SET headIconUrl = ? where userId = ?";
			Object[] params_headImg = {headImg_path,userId};
			qr.update(sql_headImg, params_headImg);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
