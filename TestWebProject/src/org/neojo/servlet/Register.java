package org.neojo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neojo.entity.Result;
import org.neojo.entity.User;
import org.neojo.service.UserService;
import org.neojo.service.impl.UserServiceImpl;

import com.google.gson.Gson;

public class Register extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// String URI = req.getRequestURI();
		String username = req.getParameter("username");
		UserService us = new UserServiceImpl();
		int status = us.CheckUserStatus(username);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		if (username != null && username.length()>=5) {
			if (status == 0) {
				out.print(new Gson().toJson(new Result(0, "可以使用此用户名")));
			} else {
				out.print(new Gson().toJson(new Result(-1, "此用户名已被注册")));
			}
		} else {
			out.print(new Gson().toJson(new Result(-999, "你们想干什么")));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		UserService us = new UserServiceImpl();
		User user = new User(0, name, 0, phone, "null", "null", "null", 0, 0, null, username, password, 1);
		int reg = us.Register(user);
		PrintWriter out = resp.getWriter();
		out.print(new Gson().toJson(new Result(reg, "注册成功")));
	}

}
