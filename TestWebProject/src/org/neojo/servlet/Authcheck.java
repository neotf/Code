package org.neojo.servlet;

import java.io.IOException;
//import java.io.PrintWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			Cookie cookies = new Cookie("username", username);
			resp.addCookie(cookies);
		}else if(login == 0){
			out.println("User not exist");
		}else{
			out.println("Error: User error "+login);
		}
		


	}
}
