package com.xkx.newssystem.user.service;

import java.util.Date;
import java.util.List;

import com.xkx.newssystem.tools.PageBean;
import com.xkx.newssystem.user.dao.UserDao;
import com.xkx.newssystem.user.domain.User;
import com.xkx.newssystem.user.domain.UserInformation;

/**
 * User业务层
 * 
 * @author XuKexiang
 *
 */
public class UserService {
	private UserDao userDao = new UserDao();

	/**
	 * 注册功能
	 * 
	 * @param user
	 * @throws UserException
	 */
	public void regist(User form) throws UserException {
		// 查找是否有同名
		User user = userDao.findByUsername(form.getName());

		// 若有同名,注册失败，抛出异常
		if (user != null) {
			throw new UserException("该用户名已经注册过了");
		}
		// 否则，添加用户
		else {
			userDao.addUser(form);
		}

	}

	/**
	 * 登录功能
	 * 
	 * @param form
	 * @return
	 * @throws UserException
	 */
	public User login(User form) throws UserException {
		// 根据用户名查找用户
		User user = userDao.findByUsername(form.getName());

		// 若用户不存在，抛出异常
		if (user == null) {
			throw new UserException("该用户名不存在");
		} else if (!user.getPassword().equals(form.getPassword())) {
			throw new UserException("密码输出错误，请重新输入");
		} else if (user.getEnable().equals("stop")) {
			throw new UserException("该用户账号已被停用，请联系管理员!");
		}
		return user;
	}

	/**
	 * 查询用户列表
	 * 
	 * @param order
	 * @param pc
	 * @param ps
	 * @return
	 */
	public PageBean<User> findAllUser(String order, int pc, int ps) {
		return userDao.findAllUser(order, pc, ps);
	}

	/**
	 * 更改用户账号状态
	 * 
	 * @param user
	 */
	public void changeEnable(String userId, String enable) {
		userDao.changeEnable(userId, enable);
	}

	/**
	 * 、 多条件查询
	 * 
	 * @param criteria
	 * @param pc
	 * @param ps
	 * @param lowDate
	 * @param upDate
	 * @return
	 */
	public PageBean<User> searchUser(User criteria, int pc, int ps, String lowDate, String upDate, String order) {
		return userDao.searchUser(criteria, pc, ps, lowDate, upDate, order);
	}

	/**
	 * 删除用户
	 * @param userId
	 */
	public void deleteUser(String userId) {
		userDao.deleteUser(userId);
		
	}

	/**
	 * 修改密码
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(String userId, String newPassword) {
		userDao.changePassword(userId,newPassword);
	}

	/**
	 * 查询个人信息
	 * @param userId
	 * @return
	 */
	public UserInformation getInformation(String userId) {
		return userDao.getInformation(userId);
	}

	/**
	 * 修改个人信息和头像
	 * @param userInformation
	 * @param userId
	 * @param headImg_path
	 */
	public void modifyInformation(UserInformation userInformation, String userId, String headImg_path) {
		userDao.modifyInformation(userInformation,userId,headImg_path);
		
	}
}
