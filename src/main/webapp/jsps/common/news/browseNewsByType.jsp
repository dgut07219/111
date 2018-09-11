<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="myTag" uri="/WEB-INF/tag/myTagLib.tld"%>
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
    <h1 align="center">${newsType}</h1>
	<div class="newsRight" align="center">
		<table border="0" >
			<tbody>
				<c:forEach items="${pb.beanList}" var="news">
					<tr>
						<td><a
							href="<c:url value='/NewsServlet?method=getNewsAndComment&newsId=${news.newsId}'/>">
								<c:choose>
									<c:when test="${ fn:length(news.title)> 32}">  
								        ${ fn:substring(news.title,0,32-1)}...
								    </c:when>
									<c:when test="${ fn:length(news.title)<=32}"> 
										${ news.title}
								    </c:when>
								</c:choose>
						</a></td>
						<td align="right" width="300"><myTag:LocalDateTimeShow
								dateTime="${news.newsTime}" type="YMDHM" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<hr>
	<center>
		<a
			href="<c:url value="/NewsServlet?pc=1&method=findNewsByNewsType&name=${newsType}"/>">首页</a>
		<c:if test="${pb.pc > 1}">
			<a
				href="<c:url value="/NewsServlet?pc=${pb.pc-1}&method=findNewsByNewsType&name=${newsType}"/>">上一页</a>
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
						href="<c:url value="/NewsServlet?pc=${i}&method=findNewsByNewsType&name=${newsType}"/>">[${i}]
					</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<c:if test="${pb.pc < pb.tp}">
			<a
				href="<c:url value="/NewsServlet?pc=${pb.pc+1}&method=findNewsByNewsType&name=${newsType}"/>">下一页</a>
		</c:if>
		<a
			href="<c:url value="/NewsServlet?pc=${pb.tp}&method=findNewsByNewsType&name=${newsType}"/>">尾页</a>
	</center>
	<div align="center">
		<jsp:include page="/jsps/common/bottom.jsp" /></div>
</body>
</html>
