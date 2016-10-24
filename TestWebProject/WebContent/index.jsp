<%@page import="java.net.URLDecoder"%><%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>TestPage</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/main.css" />
		<script type="text/javascript" src="js/jquery-2.1.0.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
		<script type="text/javascript" src="js/bootstrap.js"></script>
	</head>

	<body>
		<div id="top">
			<div id="topbox" align="center">
				<div id="userbar" align="right">

					<a href='javascript:void(0);' onclick='loginbox();' class='color1'>登陆</a>&nbsp;|&nbsp;
					<a href='javascript:void(0);' onclick='regbox();' class='color1'>注册</a>

				</div>
			</div>
		</div>
		<div id="container">
			<div id="header">
				<div>
					<font size="8">测试</font>
				</div>
			</div>

			<div id="main">
				<div id="mailbox">
					<span id="reg_tip">
						<form id="form">
							用户名：<input type="text" name="username" /></br>
							密码：<input type="password" name="password" /></br>
							<input type="button" value="登录" id="submit" />
						</form>
					</span>
					<div id="result"></div>
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

				</div>
			</div>

		</div>
		<!-- <div id="footer">啊啊啊</div> -->

	</body>

</html>