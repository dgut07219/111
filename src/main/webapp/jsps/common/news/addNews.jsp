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

<title>添加新闻</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" charset="utf-8"
	src="utf8-jsp/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="utf8-jsp/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8"
	src="utf8-jsp/lang/zh-cn/zh-cn.js"></script>

</head>

<body>
	<h1 align="center">${msg}</h1>
	<form action="<c:url value="/NewsServlet?method=addNews"/>"
		name="form1" method="post" onsubmit="return submit1()">
		<table width="600" align="center" border="1">
			<tbody>
				<tr>
					<td>标题：</td>
					<td><input type="text" name="title" id="title"></td>
				</tr>
				<tr>
					<td>类型：</td>
					<td><select name="newsType" id="newsType">
							<c:forEach items="${newsTypes}" var="newsType">
								<option value="${newsType.name}">${newsType.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>作者：</td>
					<td><input type="text" name="author" id="author"></td>
				</tr>
				<tr>
					<td>日期：</td>
					<td><input type="datetime-local" name="newsTime" id="newsTime"></td>
				</tr>
				<tr>
					<td colspan="2">
						<div>
							<script id="container" type="text/plain"
								style="width:800px;height:500px;"></script>
						</div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" value="添加新闻"></td>
				</tr>
			</tbody>
		</table>
	</form>
	<script type="text/javascript">
	
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		var ue = UE.getEditor('container');
	</script>
</body>
</html>
