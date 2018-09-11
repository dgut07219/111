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

<title>修改信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>

<style type="text/css">
h1 {
	font-size: 30px;
	color: #930;
	text-align: center;
}

td {
	color: blue;
	font-size: 20px
}
</style>

</head>
<body>
	<form
		action="<c:url value='/UserServlet?method=modifyInformation&userInformationId=${session_userInformation.userInformationId}&userId=${session_user.userId}&type=${session_user.type}'/>"
		method="post" enctype="multipart/form-data">
		<table width="800" border="1" align="center" cellspacing="0"
			cellpadding="0" height="400">
			<tbody>
				<tr>
					<td colspan="2" align="center">修改信息</td>
				</tr>
				<tr>
					<td>头像：</td>
					<td><img src="${session_user.headIconUrl}" height="100" /><br>预览：<img
						id="myImage" height="100" /><input id="myFile" name="myFile"
						type="file" onchange="preview()"></td>
				</tr>
				<c:if test="${session_user.type =='user'}">
					<tr>
						<td>性别：</td>
						<td><select name="sex" id="sex">
								<option value="男">男</option>
								<option value="女">女</option>
						</select></td>
					</tr>
					<tr>
						<td>爱好：</td>
						<td><input type="text" name="hobby"
							value="${session_userInformation.hobby}" /></td>
					</tr>
				</c:if>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="修改" /></td>
				</tr>
			</tbody>
		</table>
	</form>
</html> 


<script language=javascript>
		function preview() {
		 	var preview = document.getElementById("myImage");
		 	var file  = document.getElementById("myFile").files[0];
		 	var reader = new FileReader();
		 	reader.onloadend = function () {
		  		preview.src = reader.result;
		 	}
		 	
			if (file) 
			  	reader.readAsDataURL(file);
			else 
			  	preview.src = "";			
		}
	</script>
