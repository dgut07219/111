<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<base href="<%=basePath%>">

<title>${news.title}</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="/newssystem/css/1.css" rel="stylesheet" type="text/css">
</head>

<body>
	<center>
		<table style="width:800px;margin-top:30px;" align="center">
				<tbody>
					<tr>
						<td align="center"><h2>${news.title}</h2></td>
					<tr>
						<td align="center" height="50">作者：${news.author}&nbsp;
							&nbsp;&nbsp; &nbsp;&nbsp; &nbsp; 发布时间：<myTag:LocalDateTimeShow
								dateTime="${news.newsTime}" type="YMDHM" />
						</td>
					</tr>
					<tr>
						<td height="30"><hr></td>
					</tr>
					<tr>
						<td>${news.content}</td>
					</tr>
					<tr>
						<td height="30"><hr></td>
					</tr>
				</tbody>
		</table>
	</center>
	<%--评论 --%>
	<jsp:include page='/jsps/common/comment/addComment.jsp' flush="true">
		<jsp:param value="${news}" name="news" />
	</jsp:include>
	<hr>
	<div class="center" style="width:800px">
		<a name="commentsStart"></a>
		<div class="commentsHead">最新评论</div>
		<c:forEach items="${news.pb.beanList}" var="comment">
			<div style="margin-bottom: 10px;">
				<div style="height: 50px">
					<div class="commentIcon">
						<img width="35" src="${comment.headIconUrl}">
					</div>
					<div class="comment1">
						<div class="commentAuthor">${comment.userName}(第${comment.stair}楼)</div>
						<div class="commentTime">
							<myTag:TimestampTag dateTime="${comment.time}" type="latest" />
						</div>
					</div>
					<div class="comment2">
						<div class="comment4">
							<a class="commentPraiseA"
								href="<c:url value='/NewsServlet?method=addPraise&pc=${news.pb.pc}&newsId=${news.newsId}&commentId=${comment.commentId}'/>">
							</a>(${comment.praise}) 
							<c:if test="${comment.userName eq session_user.name}">
							<a class="commentReplay"
								href="<c:url value='/CommentServlet?method=deleteComment&newsId=${news.newsId}&commentId=${comment.commentId}&pc=${news.pb.pc}'/>">删除</a>
								</c:if>
						</div>
						<div class="comment3">
							<a class="commentReplay"
								href="<c:url value='/NewsServlet?method=addReplyPre&newsId=${news.newsId}&commentId=${comment.commentId}&pc=${news.pb.pc}'/>">
								回复 </a>
						</div>
					</div>
				</div>
				<div class="clear">
					<div class="comment5 ">
						<div class="commentContent">${comment.content}</div>
					</div>
				</div>
			</div>
		</c:forEach>
		</div>
		<hr>
		<center>
			<a
				href="<c:url value="/NewsServlet?pc=1&method=getNewsAndComment&newsId=${news.newsId}"/>">首页</a>
			<c:if test="${news.pb.pc > 1}">
				<a
					href="<c:url value="/NewsServlet?pc=${news.pb.pc-1}&method=getNewsAndComment&newsId=${news.newsId}"/>">上一页</a>
			</c:if>
			<%--显示页码 --%>
			<c:choose>
				<%--当总页数不足10页时，全部显示出来 --%>
				<c:when test="${news.pb.tp < 10}">
					<c:set var="begin" value="1" />
					<c:set var="end" value="${news.pb.tp}" />
				</c:when>
				<%--当总页数大于10页时，用公式计算出页码 --%>
				<c:otherwise>
					<c:set var="begin" value="${news.pb.pc-5}" />
					<c:set var="end" value="${news.pb.pc+4}" />
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
							href="<c:url value="/NewsServlet?pc=${i}&method=getNewsAndComment&newsId=${news.newsId}"/>">[${i}]
						</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:if test="${news.pb.pc < news.pb.tp}">
				<a
					href="<c:url value="/NewsServlet?pc=${news.pb.pc+1}&method=getNewsAndComment&newsId=${news.newsId}"/>">下一页</a>
			</c:if>
			<a
				href="<c:url value="/NewsServlet?pc=${news.pb.tp}&method=getNewsAndComment&newsId=${news.newsId}"/>">尾页</a>
		</center>
	<div align="center">
		<jsp:include page="/jsps/common/bottom.jsp" /></div>
</body>
</html>
