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
	
	bar1.add("用户管理", "查看用户", "<c:url value='/UserServlet?method=findAllUser&order=asc'/>", "body");
	bar1.add("用户管理", "审查用户", "<c:url value='/UserServlet?method=changeEnable&order=asc'/>", "body");
	bar1.add("用户管理", "删除用户", "<c:url value='/UserServlet?method=deleteUser&order=asc'/>", "body");
	bar1.add("用户管理", "查询用户", "<c:url value='/jsps/admin/searchUser.jsp'/>", "body");
	
	bar1.add("新闻管理", "浏览新闻", "<c:url value='/NewsServlet?method=findAllNews&order=asc'/>", "body");
	bar1.add("新闻管理", "管理新闻", "<c:url value='/NewsServlet?method=manageNews&order=asc'/>", "body");
	
	bar1.add("分类管理", "查看新闻分类", "<c:url value='/NewsServlet?method=findAllNewsTypeForManager'/>", "body");
	bar1.add("分类管理", "添加新闻分类", "<c:url value='/jsps/admin/addNewsType.jsp'/>", "body");
	
	bar1.add("评论管理", "查看所有评论", "<c:url value='/CommentServlet?method=findAllComments'/>", "body");

	var d = document.getElementById("menu");
	d.innerHTML = bar1.toString();
}
</script>

</head>

<body onload="load()" style="margin: 0px; background: rgb(254,238,189);">
<div id="menu"></div>

</body>
</html>
