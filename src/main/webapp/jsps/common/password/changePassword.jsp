<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'changePassword.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<form action="<c:url value='/UserServlet?method=changePassword&userId=${session_user.userId}&name=${session_user.name}'/>"
	method="post" onsubmit="return submit1()">
	<table width="800" border="0" align="center">
			<tbody>
				<tr>
					<td></td>
					<td>修改密码</td>
				</tr>		
				<tr>
					<td align="right">旧密码：</td>
					<td align="left"><input type="password" name="oldPassword" id="oldPassword" value='${oldPassword}'/><span style="color: red; font-weight: 900">${errors.oldPassword}</span></td>
				</tr>
				<tr>
					<td align="right">新密码：</td>
					<td align="left"><input type="password" name="newPassword" id="newPassword" value='${newPassword}'/><span style="color: red; font-weight: 900">${errors.newPassword}</span></td>
				</tr>
				<tr>
					<td align="right">再次输入新密码：</td>
					<td align="left"><input type="password" name="newPassword1" id="newPassword1" value='${newPassword1}'/><span style="color: red; font-weight: 900">${errors.newPassword1}</span></td>
				</tr>
				<tr>
					<td></td><td><input type="submit" value="submit"/></td>
				</tr>								
			</tbody>
		</table>
	</form>    
  </body>
</html>
