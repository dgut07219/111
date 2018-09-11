<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>菜单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/menu/mymenu.css'/>" type="text/css" media="all">
<script language="javascript">
var bar1 = new Q6MenuBar("bar1", "菜单导航");
function load() {
	bar1.colorStyle = 2;
	bar1.config.imgDir = "<c:url value='/menu/img/'/>";
	bar1.config.radioButton=false;
	bar1.add("个人信息管理", "查看个人信息", "<c:url value='/UserServlet?method=getInformation&name=${session_user.name}&userId=${session_user.userId}&type=${session_user.type}'/>", "body");
	bar1.add("个人信息管理", "修改个人信息", "<c:url value='/jsps/common/information/modifyInformation.jsp'/>", "body");
	bar1.add("个人信息管理", "修改密码", "<c:url value='/jsps/common/password/changePassword.jsp'/>", "body");
	
	bar1.add("新闻管理", "浏览新闻", "<c:url value='/NewsServlet?method=findAllNews&order=asc'/>", "body");
	bar1.add("新闻管理", "发布新闻", "<c:url value='/NewsServlet?method=findAllNewsType' />", "body");
	bar1.add("新闻管理", "编辑新闻", "<c:url value='/NewsServlet?method=authorManageNews&order=asc' />", "body");

	var d = document.getElementById("menu");
	d.innerHTML = bar1.toString();
}
</script>

</head>

<body onload="load()" style="margin: 0px; background: rgb(254,238,189);">
<div id="menu"></div>

</body>
</html>
