package org.neojo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neojo.Exception.LoginException;
import org.neojo.entity.Dormitory;
import org.neojo.entity.Result;
import org.neojo.service.ElectricityQuery;

import com.google.gson.Gson;

/**
 * Servlet implementation class Query
 */
@WebServlet("/Query")
public class Query extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private boolean checkRoom(Integer build, Integer floor, Integer room) {
		if (build < 1 || floor < 1 || room < 1) {
			return false;
		}
		if (build <= Dormitory.ROOMS.length && floor <= Dormitory.ROOMS[build - 1].length
				&& room <= Dormitory.ROOMS[build - 1][floor - 1]) {
			return true;
		}
		return false;
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Query() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		String sbuild = request.getParameter("build");
		String sfloor = request.getParameter("floor");
		String sroom = request.getParameter("room");
		String sday = request.getParameter("day");
		String __VIEWSTATE = request.getParameter("vs");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		if (type == null || sbuild == null || sfloor == null || sroom == null) {
			out.print(new Gson().toJson(new Result(-1, "Null Value")));
		} else {
			int build = 0, floor = 0, room = 0, day = 0;
			try {
				build = Integer.parseInt(sbuild);
				floor = Integer.parseInt(sfloor);
				room = Integer.parseInt(sroom);
				day = Integer.parseInt(sday == null ? "0" : sday);
				if (checkRoom(build, floor, room)) {
					ElectricityQuery qur = new ElectricityQuery(new Dormitory(build, floor, room));
					try {
						if (__VIEWSTATE == null) {
							if ("balance".equals(type)) {
								out.print(qur.checkBalance());
							} else if ("uselog".equals(type)) {
								out.print(qur.checkUsedLog(day == 0 ? 7 : day));
							} else if ("buylog".equals(type)) {
								out.print(qur.checkBuyLog(day == 0 ? 300 : day));
							} else if ("getvs".equals(type)){
								out.print(qur.getViewState());
							}
						} else {
							if ("balance".equals(type)) {
								out.print(qur.checkBalance(__VIEWSTATE));
							} else if ("uselog".equals(type)) {
								out.print(qur.checkUsedLog(__VIEWSTATE, day == 0 ? 7 : day));
							} else if ("buylog".equals(type)) {
								out.print(qur.checkBuyLog(__VIEWSTATE, day == 0 ? 300 : day));
							}
						}
					} catch (LoginException e) {
						out.print(new Gson().toJson(new Result(-2, e.getMessage())));
					}
				} else {
					out.print(new Gson().toJson(new Result(-3, "None Room")));
				}
			} catch (NumberFormatException e) {
				out.print(new Gson().toJson(new Result(-4, "Error Value")));
			}
		}
	}
}
