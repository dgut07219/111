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

<title>My JSP 'searchUser.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>
	<form
		action="<c:url value='/UserServlet?method=searchUser&order=asc'/>"
		method="post">
		<input type="hidden" name="order" value="asc" />
		<h1 style="color: blue;" align="center">查询条件</h1>
			<table width="600" height="170" border="0" align="center"
				cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td align="center" width="120">用户类型：</td>
						<td><select name="type" style="width: 120;height: 30">
								<option value="all">全部</option>
								<option value="user">用户</option>
								<option value="newsAuthor">新闻发布员</option>
								<option value="manager">管理员</option>
						</select></td>
					</tr>
					<tr>
						<td width="116" align="center">用户名:</td>
						<td width="120" align="left"><input type="text" name="name" style="width: 200;height: 30"
							id="name"><span id="namespan"></span></td>
					</tr>
					<tr>
						<td align="center" width="200">帐号可用性:</td>
						<td align="left"><select name="enable" id="select"  style="width: 120;height: 30">
								<option value="all">全部</option>
								<option value="use">可用</option>
								<option value="stop">停用</option>
						</select> <span id="passwordspan"></span></td>
					</tr>
					<tr>
						<td align="center">注册日期：</td>
						<td align="left">介于<input type="date" name="lowDate" style="width: 120;height: 30">与<input
							type="date" name="upDate" style="width: 120;height: 30">之间
						</td>
					</tr>
					<br>
					<tr>
						<td></td>
						<td align="left"><input type="submit" value="查询"  style="width: 70;height: 30;background: yellow;"/></td>
					</tr>
				</tbody>
			</table>
	</form>
</body>
</html>
