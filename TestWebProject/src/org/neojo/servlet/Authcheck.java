package org.neojo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.neojo.entity.Result;
import org.neojo.entity.User;
import org.neojo.service.UserService;
import org.neojo.service.impl.UserServiceImpl;

import com.google.gson.Gson;

public class Authcheck extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		UserService us = new UserServiceImpl();
		int login = us.Login(username, password);
		PrintWriter out = resp.getWriter();
		if (login > 0) {
			User user = us.GetUser(login);
			HttpSession session = req.getSession();
			session.setAttribute("uid", user.getUid());
			session.setAttribute("deptno", user.getDeptno());
			session.setAttribute("job", user.getJob());
			session.setMaxInactiveInterval(60 * 60 * 24);
			Cookie cookie = new Cookie("name", URLEncoder.encode(user.getName(), "UTF-8"));
			cookie.setDomain("local.neojo.org");
			resp.addCookie(cookie);
			out.print(new Gson().toJson(user));
		} else if (login == 0) {
			out.print(new Gson().toJson(new Result(login, "用户不存在")));
		} else {
			switch (login) {
			case -1:
				out.print(new Gson().toJson(new Result(login, "密码错误")));
				break;
			default:
				out.print(new Gson().toJson(new Result(login, "未知错误")));
				break;
			}
		}
	}
}
