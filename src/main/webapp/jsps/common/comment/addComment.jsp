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

<title>My JSP 'addComment.jsp' starting page</title>
<script src="js/xml.js"></script>

</head>
<style>
.main {
	width: 800px;
	margin: 20px auto;
}

span {
	display: inline-block;
	width: 200px;
	height: 25px;
	line-height: 25px;
	vertical-align: center;
	text-align: left;
	margin-bottom: 10px;
}

.tag {
	font-size: 13px;
	margin-left: 370px;
	vertical-align: bottom;
	text-align: center;
	margin-bottom: 0;
}

.text {
	width: 750px;
	height: 180px;
	margin: 0 auto;
	resize: none;
}

input {
	display: inline-block;
	width: 80px;
	height: 50px;
	background: #E2526F;
	border: 0;
	margin-left: 670px;
	margin-top: 10px;
}

.creatediv {
	width: 675px;
	height: 80px;
	border: 1px solid gray;
	position: relative;
	margin-top: 10px;
	padding-left: 75px;
}

.createinput {
	width: 80px;
	height: 30px;
	position: absolute;
	right: 5px;
	bottom: 5px;
}

.createimg {
	width: 50px;
	height: 50px;
	position: absolute;
	top: 15px;
	left: 15px;
}

.createdivs {
	width: 600px;
	height: 50px;
	position: absolute;
	top: 15px;
	left: 85px;
}
</style>

<body>
	<div class="main">
		<form
			action="<c:url value='/NewsServlet?method=addComment&newsId=${news.newsId}&userName=${session_user.name}&pc=${news.pb.pc}&isReply=0' />"
			method="post">
			<span>你想对楼主说点什么....</span> <span class="tag">你最多可以输入30个字符</span>
			<textarea id="text" cols="30" rows="10" name="content" maxlength="30"
				class="text"></textarea>
			<br> <input type="submit" value="发 表" id="ipt">
			<div id="txt"></div>
		</form>
	</div>
</body>


</html>
