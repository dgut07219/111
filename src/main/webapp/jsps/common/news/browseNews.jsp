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

<title>新闻界面</title>

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
		<h3>新闻列表</h3>
	</div>

	<table class="imagetable" align="center">
		<tr>
			<c:if test="${order eq 'desc' }">
				<th><a
					href="<c:url value='/NewsServlet?pc=1&order=asc&method=findAllNews'/>">新闻ID</a></th>
			</c:if>
			<c:if test="${order eq 'asc' }">
				<th><a
					href="<c:url value='/NewsServlet?pc=1&order=desc&method=findAllNews'/>">新闻ID</a></th>
			</c:if>
			<th>标题</th>
			<th>类型</th>
			<th>作者</th>
			<th>发布日期</th>
		</tr>

		<c:forEach items="${pb.beanList}" var="news">
			<tr>
				<td>${news.newsId}</td>
				<td><a href="<c:url value='/NewsServlet?method=getNewsAndComment&newsId=${news.newsId}'/>">${news.title}</a></td>
				<td>${news.newsType}</td>
				<td>${news.author}</td>
				<td>${news.publishTime}</td> 
			</tr>
		</c:forEach>
	</table>

	<hr>
	<center>
		<a
			href="<c:url value="/NewsServlet?pc=1&method=findAllNews&order=${order}"/>">首页</a>
		<c:if test="${pb.pc > 1}">
			<a
				href="<c:url value="/NewsServlet?pc=${pb.pc-1}&method=findAllNews&order=${order}"/>">上一页</a>
		</c:if>

		<%--显示页码 --%>
		<c:choose>
			<%--当总页数不足10页时，全部显示出来 --%>
			<c:when test="${pb.tp < 10}">
				<c:set var="begin" value="1" />
				<c:set var="end" value="${pb.tp}" />
			</c:when>
			<%--当总页数大于10页时，用公式计算出页码 --%>
			<c:otherwise>
				<c:set var="begin" value="${pb.pc-5}" />
				<c:set var="end" value="${pb.pc+4}" />
				<%--头溢出 --%>
				<c:if test="${begin<1}">
					<c:set var="begin" value="1" />
					<c:set var="end" value="10" />
				</c:if>
				<%--尾溢出 --%>
				<c:if test="${end>tp}">
					<c:set var="begin" value="${tp-9}" />
					<c:set var="end" value="${tp}" />
				</c:if>
			</c:otherwise>
		</c:choose>


		<c:forEach var="i" begin="${begin}" end="${end}">
			<c:choose>
				<c:when test="${i eq pb.pc}">
		  [${i}]
		  </c:when>
				<c:otherwise>
					<a
						href="<c:url value="/NewsServlet?pc=${i}&method=findAllNews&order=${order}"/>">[${i}]
					</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<c:if test="${pb.pc < pb.tp}">
			<a
				href="<c:url value="/NewsServlet?pc=${pb.pc+1}&method=findAllNews&order=${order}"/>">下一页</a>
		</c:if>
		<a
			href="<c:url value="/NewsServlet?pc=${pb.tp}&method=findAllNews&order=${order}"/>">尾页</a>
	</center>
</body>
</html>
