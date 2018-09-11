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

<title>My JSP 'showImformain.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
h1 {
	font-size: 30px;
	color: #930;
	text-align: center;
}

td {
	color: blue;
	font-size: 20px
}
</style>

</head>
<body>
	<h1 align="center">我的信息</h1>
	<table width="600" border="0" align="center" cellspacing="0"
		cellpadding="0">
		<tbody align="center">
			<tr>
				<td>头像：</td>
				<td><img src="<c:url value='/${session_user.headIconUrl}'/>"
					height="100" /></td>
			</tr>
			<tr>
				<td>用户类型：</td>
				<td>${session_user.type}</td>
			</tr>
			<tr>
				<td>用户名：</td>
				<td>${session_user.name}</td>
			</tr>
			<tr>
				<td>注册日期：</td>
				<td>${session_user.registerDate}</td>
			</tr>
			<c:if test="${session_user.type =='user'}">
				<tr>
					<td>性别：</td>
					<td>${session_userInformation.sex}</td>
				</tr>
				<tr>
					<td>爱好：</td>
					<td>${session_userInformation.hobby}</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</body>
</html>
