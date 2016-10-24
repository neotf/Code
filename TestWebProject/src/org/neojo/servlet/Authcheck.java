package org.neojo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neojo.entity.User;
import org.neojo.service.UserService;
import org.neojo.service.impl.UserServiceImpl;

public class Authcheck extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		UserService us = new UserServiceImpl();
		int login = us.Login(username, password);

		PrintWriter out = resp.getWriter();
		if (login > 0) {
			out.println("Login success");
			User user = us.GetUser(login);
			Cookie cookie = new Cookie("name", URLEncoder.encode(user.getName(),"UTF-8"));
			cookie.setDomain("local.neojo.org");
			resp.addCookie(cookie);
		}else if(login == 0){
			out.println("User not exist");
		}else{
			out.println("Error: User error "+login);
		}
		


	}
}
