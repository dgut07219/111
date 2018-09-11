<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<title>回复</title>

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

	<table align="center" border='1'>
			<tr>
				<td>回复第${comment.stair}楼层<br>${comment.userName}于<fmt:formatDate type="both"
							dateStyle="long" timeStyle="long" value="${comment.time}" />发表评论:
						${comment.content}	
				</td>
			</tr>
	</table>
	<form
		action="<c:url value='/NewsServlet?method=addComment&newsId=${newsId}&userName=${session_user.name}&pc=${pc}&isReply=1&userName1=${comment.userName}&stair=${comment.stair}' />"
		method="post">
		<h4 align="center">回复:</h4>
		<table border="1" align="center" cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
					<td><textarea name="content" cols="60" rows="8" id="textarea"
							placeholder="回复@${comment.userName}:"/></textarea></td>
				</tr>
				<tr>
					<td><input type="submit" name="submit" id="submit" value="提交"></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
