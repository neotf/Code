package org.neojo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neojo.dao.action;

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
		action a = new action();
		int userstatus = a.checkUser(username);
		
		PrintWriter out = resp.getWriter();
		
		if(userstatus>0){
			a.checkPassword(username, password);
		}else{
			out.println("�û�״̬�쳣");
		}
		
//		
//		out.println(username+"״̬:"+a.checkUser(username));
//		out.println(password);
		Cookie cookies = new Cookie("username", username);
		resp.addCookie(cookies);
	}
}
