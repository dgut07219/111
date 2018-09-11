<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>top</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

#content ul {
	width: 980px;
	height: 40px;
	margin: auto;
	position: relative;
}

ul li {
	list-style: none;
	float: left;
	position: relative;
	z-index: 5;
	/*width: 60px;*/
	/*text-align: center;*/
}

ul li a {
	text-decoration: none;
	color: white;
	font-size: 14px;
	line-height: 40px;
	padding: 0 10px;
	margin-right: 2px;
}

#slider {
	width: 50px;
	height: 40px;
	background: #c00;
	position: absolute;
	top: 0;
	left: 0;
}
</style>

</head>

<body>
	<h1 style="text-align: center;;color: white;">Skrrrrr新闻网</h1>
	<div align="left">
		<ul>
			<c:choose>
				<c:when test="${empty session_user}">
					<li><a href="<c:url value="/jsps/user/login.jsp"/>"
						target="_parent">登录 |</a></li>
					<li><a href="<c:url value="/jsps/user/regist.jsp"/>"
						target="_parent">注册</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="">用户：${sessionScope.session_user.name}</a></li>
					<li><a href="<c:url value='/UserServlet?method=quit'/>"
						target="_parent">注销</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
	<div id='content'>
		<ul>
			<div id="slider"></div>
			<li style="background: #CC0000;"><a
				href="<c:url value="/NewsServlet?method=findAllNews&pc=1&type=user"/>" target="body">首页</a></li>
			<c:forEach items="${newsTypes}" var="newsType">
				<li><a href="<c:url value='/NewsServlet?method=findNewsByNewsType&name=${newsType.name}'/>" target="body"/>${newsType.name}</a></li>
			</c:forEach>
		</ul>
	</div>


</body>

<script src="jquery/jquery-2.2.4.min.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript">
	$("li").mouseenter(function() {
		$("#slider").animate({
			left : $(this).offset().left - $('li').eq(0).offset().left,
		}, 50)
		$("#slider").css({
			width : $(this).width(),
		})

	})

	$("ul").mouseleave(function() {
		$("#slider").css({
			width : '50',
		})
		$("#slider").animate({
			left : "0",
		}, 200)
	})
</script>

</html>
