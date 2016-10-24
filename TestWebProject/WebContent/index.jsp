<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-2.1.0.js" ></script>
<script type="text/javascript" src="js/main.js" ></script>
</head>
<body>
	<%
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			out.print("没有Cookie信息");
		} else {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				String name = cookie.getName();
				String value = cookie.getValue();
				out.print(name + ":" + URLDecoder.decode(value, "UTF-8") + "<br>");
			}
			
		}
	%>
	<form id="form">
		<input type="text" name="username"/><br>
		<input type="password" name="password"/><br>
		<input type="button" value="登录" onclick="login()" />
	</form>
	<div id="result"></div>
</body>
</html>