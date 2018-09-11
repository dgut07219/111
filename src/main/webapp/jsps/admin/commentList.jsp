<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="myTag" uri="/WEB-INF/tag/myTagLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
table.imagetable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}

table.imagetable th {
	background: #b5cfd2 url('cell-blue.jpg');
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
}

table.imagetable td {
	background: #dcddc0 url('cell-grey.jpg');
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
}
</style>
<base href="<%=basePath%>">

<title>评论列表</title>

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
	<div align="center">
		<h3>评论列表</h3>
	</div>

	<table class="imagetable" align="center">
		<tr>
			<th>评论ID</th>
			<th>用户</th>
			<th>内容</th>
			<th>时间</th>
			<th>赞数</th>
			<th>操作</th>
			
		</tr>

		<c:forEach items="${commentList}" var="comment">
			<tr>
				<td>${comment.commentId}</td>
				<td>${comment.userName}</td>
				<td>${comment.content}</td>
				<td><myTag:TimestampTag dateTime="${comment.time}" type="latest" /></td>
				<td>${comment.praise}</td>
				<td><a href='<c:url value="/CommentServlet?method=delete&commentId=${comment.commentId}" />'>删除</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
