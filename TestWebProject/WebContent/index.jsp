<%@page import="java.net.URLDecoder"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
					<a href='javascript:void(0);' id="btn_add" class='color1'>注册</a>

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
					<!--<table>
						<form id="login">
							<tr><td>用户名：</td><td><input type="text" name="username" /></td></tr>
							<tr><td>密码：</td><td><input type="password" name="password" /></td></tr>
							<tr><td colspan="2"><input type="button" value="登录" id="logsub" /></td></tr>
							<tr><td colspan="2"><div id="result"></div></td></tr>
						</form>
						

					</table>-->

					<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
									<h4 class="modal-title" id="myModalLabel">新增</h4>
								</div>
								<div class="modal-body">
									<form id="register">
									<div class="form-group">
										<label for="username">用户名</label>
										<input type="text" name="username" id="username" onchange="checkuser();" class="form-control" placeholder="用户名">
									</div>
									<div class="form-group">
										<label for="password">密码</label>
										<input type="password" name="password" class="form-control" id="password" placeholder="密码">
									</div>
									<div class="form-group">
										<label for="name">姓名</label>
										<input type="text" name="name" class="form-control" placeholder="姓名">
									</div>
									<div class="form-group">
										<label for="phone">手机号</label>
										<input type="text" name="statu" class="form-control" placeholder="手机号">
									</div>
									</form>
								</div>
								<div class="modal-footer">
									<button type="button" id="btn_reg" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>注册</button>
								</div>
							</div>
						</div>
					</div>
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
					<span id="reg_tip">
						<form id="register">
							姓名：<input type="text" name="name" /></br>
							手机：<input type="text" name="phone" /></br>
							用户名：<input type="text" name="username" /></br>
							密码：<input type="password" name="password" /></br>
							<input type="button" value="注册" id="regsub" />
						</form>
					</span>
					<div id="result"></div>

				</div>
			</div>

		</div>
		<!-- <div id="footer">啊啊啊</div> -->

	</body>

</html>