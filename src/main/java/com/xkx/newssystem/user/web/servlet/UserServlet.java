package com.xkx.newssystem.user.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Session;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.xkx.newssystem.authority.tools.AuthorityTool;
import com.xkx.newssystem.news.domain.NewsType;
import com.xkx.newssystem.news.service.NewsService;
import com.xkx.newssystem.tools.PageBean;
import com.xkx.newssystem.user.dao.UserDao;
import com.xkx.newssystem.user.domain.User;
import com.xkx.newssystem.user.domain.UserInformation;
import com.xkx.newssystem.user.service.UserException;
import com.xkx.newssystem.user.service.UserService;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * UserWeb层
 * 
 * @author XuKexiang
 *
 */
public class UserServlet extends BaseServlet {
	// 依赖Service层
	private UserService userService = new UserService();
	private UserDao userDao = new UserDao();
	private NewsService newsService = new NewsService();
	HttpServletRequest request;
	/**
	 * 注册功能
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws UserException
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 封装表单数据
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		String password1 = request.getParameter("password1");
		
		// 校验表单数据
		// 创建一个Map，用来封装错误信息，其中key为表单字段名称，值为错误信息
		Map<String, String> errors = new HashMap<String, String>();

		String name = form.getName();
		if (name == null || name.trim().isEmpty()) {
			errors.put("name", "用户名不能为空");
		} else if (name.length() < 3 || name.length() > 10) {
			errors.put("name", "用户名长度应该为3~10位");
		}

		String password = form.getPassword();

		if (password == null || password.trim().isEmpty()) {
			errors.put("password", "密码不能为空");
		} else if (password.length() < 3 || password.length() > 16) {
			errors.put("password", "密码长度应该为3~16位");
		}

		// 校验第二次输入的新密码
		if (!password.equals(password1)) {
			errors.put("password1", "两次输入密码不一致");
		}
		
		/*
		 * 判断是否存在错误信息,有的话就返回注册界面重新注册
		 */
		if (errors.size() > 0) {
			// 保存错误信息
			request.setAttribute("errors", errors);

			// 保存表单数据
			request.setAttribute("form", form);
			request.setAttribute("password1", password1);
			// 转发到regist.jsp
			return "f:/jsps/user/regist.jsp";
		}

		/*
		 * 没有错误信息则执行service方法
		 */
		try {
			userService.regist(form);
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}

		// 正常执行到这，表示注册成功
		request.setAttribute("msg", "恭喜您注册成功");
		return "f:/jsps/msg.jsp";
	}

	/**
	 * 登录功能
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 封装表单数据
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		try {
			/*
			 * 输入校验,用map来装错误信息
			 */
			Map<String, String> errors = new HashMap<String, String>();

			// 校验用户名
			String name = form.getName();
			if (name == null || name.trim().isEmpty()) {
				errors.put("name", "用户名不能为空");
			} else if (name.length() < 3 || name.length() > 10) {
				errors.put("name", "用户名的长度应为3~10位");
			}

			// 校验密码
			String password = form.getPassword();
			if (password == null || password.trim().isEmpty()) {
				errors.put("password", "密码不能为空");
			} else if (password.length() < 3 || password.length() > 16) {
				errors.put("password", "密码的长度应为3~16位");
			}

			// 若errors中有错误信息
			if (errors.size() > 0) {
				// 将form保存用于回显
				request.setAttribute("form", form);

				// 将错误信息保存
				request.setAttribute("errors", errors);
				// 转发到login.jsp
				return "f:/jsps/user/login.jsp";
			} else {
				User user = userService.login(form);

				// 登录成功，保存user到Session中
				request.getSession().setAttribute("session_user", user);

				/*
				 * 若user的类型为user,转发到index.jsp 若user类型为manager,转发到adminUser.jsp中
				 * 若user类型为newsAuthor转发到newAuthor.jsp中
				 */
				if (user.getType().equals("user")) {
					return "f:/jsps/user/page/main.jsp";
				} else if (user.getType().equals("manager")) {
					return "f:/jsps/admin/page/main.jsp";
				} else {
					return "f:/jsps/newsAuthor/page/main.jsp";
				}
			}

		} catch (UserException e) {
			// 保存错误信息
			request.setAttribute("msg", e.getMessage());

			// 保存表单数据form用于回显
			request.setAttribute("form", form);

			// 转发到login.jsp中
			return "f:/jsps/user/login.jsp";
		}
	}

	/**
	 * 查询用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 排序指令
		String order = request.getParameter("order");

		// 获取当前页码
		int pc = getPc(request);

		// 设置一页的记录数
		int ps = 3;

		// 得到PageBean
		PageBean<User> pb = userService.findAllUser(order, pc, ps);

		request.setAttribute("pb", pb);
		request.setAttribute("order", order);

		return "f:/jsps/admin/showUser.jsp";

	}

	public String changeEnable(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 排序指令
		String order = request.getParameter("order");

		// 获取当前页码
		int pc = getPc(request);

		// 设置一页的记录数
		int ps = 3;

		// 获取用户Id
		String userId = request.getParameter("userId");
		// 获取账号状态
		String enable = request.getParameter("enable");
		// 调用service更改账号状态
		userService.changeEnable(userId, enable);

		// 得到PageBean
		PageBean<User> pb = userService.findAllUser(order, pc, ps);

		request.setAttribute("pb", pb);
		request.setAttribute("order", order);

		return "f:/jsps/admin/checkUser.jsp";

	}

	/**
	 * 多条件查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String searchUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 排序指令
		String order = request.getParameter("order");

		// 日期
		String lowDate = request.getParameter("lowDate");
		String upDate = request.getParameter("upDate");
		// 获取当前页码
		int pc = getPc(request);

		// 设置一页的记录数
		int ps = 3;

		// 封装表单数据
		User criteria = CommonUtils.toBean(request.getParameterMap(), User.class);

		// 得到PageBean
		PageBean<User> pb = userService.searchUser(criteria, pc, ps, lowDate, upDate, order);

		request.setAttribute("pb", pb);
		request.setAttribute("order", order);

		// 保存表单数据到request域
		request.setAttribute("criteria", criteria);
		request.setAttribute("lowDate", lowDate);
		request.setAttribute("upDate", upDate);

		return "f:/jsps/admin/showSearchUser.jsp";

	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取userId
		String userId = request.getParameter("userId");
		// 排序指令
		String order = request.getParameter("order");

		// 获取当前页码
		int pc = getPc(request);

		// 设置一页的记录数
		int ps = 3;

		if (userId == null) {
			// 得到PageBean
			PageBean<User> pb = userService.findAllUser(order, pc, ps);

			request.setAttribute("pb", pb);
			request.setAttribute("order", order);
		} else {
			userService.deleteUser(userId);
			// 得到PageBean
			PageBean<User> pb = userService.findAllUser(order, pc, ps);

			request.setAttribute("pb", pb);
			request.setAttribute("order", order);
		}
		return "f:/jsps/admin/deleteUser.jsp";
	}

	/**
	 * 注销
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/jsps/user/login.jsp";
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String changePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取用户ID
		String userId = request.getParameter("userId");
		// 获取用户名
		String name = request.getParameter("name");
		// 获取旧密码
		String oldPassword = request.getParameter("oldPassword");
		// 获取第一次输入的新密码
		String newPassword = request.getParameter("newPassword");
		// 获取第二次输入的新密码
		String newPassword1 = request.getParameter("newPassword1");
		// 校验旧密码格式
		Map<String, String> errors = new HashMap<String, String>();

		if (oldPassword == null || oldPassword.trim().isEmpty()) {
			errors.put("oldPassword", "密码不能为空");
		} else {
			if (oldPassword.length() < 3 || oldPassword.length() > 16) {
				errors.put("oldPassword", "密码长度应该为3~16位");
			}
		}

		// 校验旧密码是否正确
		// 获取用户对象
		User user = userDao.findByUsername(name);
		// 检验密码
		if (oldPassword != null && !oldPassword.trim().isEmpty()) {
			if (!user.getPassword().equals(oldPassword)) {
				errors.put("oldPassword", "旧密码错误");
			}
		}

		// 校验第一次输入的新密码
		if (newPassword == null || newPassword.trim().isEmpty()) {
			errors.put("newPassword", "密码不能为空");
		} else {
			if (newPassword.length() < 3 || newPassword.length() > 16) {
				errors.put("newPassword", "密码长度应该为3~16位");
			}
		}

		// 校验第二次输入的新密码
		if (!newPassword.equals(newPassword1)) {
			errors.put("newPassword1", "两次输入密码不一致");
		}
		if (errors.size() > 0) {
			request.setAttribute("errors", errors);
			// 回显
			request.setAttribute("oldPassword", oldPassword);
			request.setAttribute("newPassword", newPassword);
			request.setAttribute("newPassword1", newPassword1);
			return "f:/jsps/common/password/changePassword.jsp";
		}

		userService.changePassword(userId, newPassword);
		return "f:/jsps/common/information/showInformation.jsp";

	}

	/**
	 * 查询个人信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getInformation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取用户名
		String name = request.getParameter("name");
		// 获取用户Id
		String userId = request.getParameter("userId");
		// 查询用户个人信息
		UserInformation userInformation = userService.getInformation(userId);
		// 查询用户
		User user = userDao.findByUsername(name);

		request.getSession().setAttribute("session_user", user);
		request.getSession().setAttribute("session_userInformation", userInformation);
		return "f:/jsps/common/information/showInformation.jsp";

	}

	/**
	 * 修改个人信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String modifyInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取用户信息Id
		String userInformationId = request.getParameter("userInformationId");
		// 获取用户Id
		String userId = request.getParameter("userId");
		// 获取用户类型
		String type = request.getParameter("type");
		/*
		 * 上传3步
		 */
		// 创建工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 得到解析器
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// 解析request对象,得到List<FileItem>
		List<FileItem> fileItemList = sfu.parseRequest(request);
		/*
		 * 把fileItemList中的数据封装到userInformation对象中 >先把普通的表单项放到Map中
		 * >再把Map中的数据放到userInformation对象中
		 */
		Map<String, String> map = new HashMap<String, String>();
		for (FileItem fileItem : fileItemList) {
			if (fileItem.isFormField()) {
				map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
			}
		}
		// 封装表单数据
		UserInformation userInformation = CommonUtils.toBean(map, UserInformation.class);
		// 设置userInformationId
		userInformation.setUserInformationId(Integer.valueOf(userInformationId));
		userInformation.setUserId(Integer.valueOf(userId));
		/*
		 * 保存上传文件 >保存的目录 >保存的文件名称
		 */
		// 保存文件目录
		String savepath = this.getServletContext().getRealPath("head_img");
		// 得到文件名
		String filename = CommonUtils.uuid() + "_" + fileItemList.get(0).getName();
		// 用文件目录和文件名创建目标文件
		File destFile = new File(savepath, filename);
		// 把上传文件放在指定的目录下
		fileItemList.get(0).write(destFile);

		/*
		 * 修改个人信息
		 */
		// 头像图片相对路径
		String headImg_path = "head_img/" + filename;
		// 调用方法修改个人信息和头像
		userService.modifyInformation(userInformation, userId, headImg_path);

		return "f:/jsps/common/information/showInformation.jsp";
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
