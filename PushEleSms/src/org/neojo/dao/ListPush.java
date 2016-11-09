package org.neojo.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.neojo.entity.Pusher;
import org.neojo.util.DBHelper;

public class ListPush {
	private String GetViewStateSql = "SELECT viewstate.viewstate FROM viewstate WHERE viewstate.build = ? AND viewstate.floor = ?";
	private String GetPushSql = "SELECT pusher.phone, pusher.build, pusher.floor, pusher.room, pusher.type FROM pusher";
	public List<Pusher> getPushList(){
		List<Pusher> pushlist = new ArrayList<>();
		DBHelper db = new DBHelper();
		int build;
		int floor;
		int room;
		String viewState;
		String phone;
		String type;
		try {
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(GetViewStateSql);
			Statement st = conn.createStatement();
			ResultSet rs =st.executeQuery(GetPushSql);
			while (rs.next()) {
				build = rs.getInt("build");
				floor = rs.getInt("floor");
				room = rs.getInt("room");
				ps.setInt(1, build);
				ps.setInt(2, floor);
				ResultSet rs2 = ps.executeQuery();
				if(rs2.next()){
					viewState = rs2.getString("viewstate");
				}else{
					viewState = null;
					
				}
				phone = rs.getString("phone");
				type = rs.getString("type");
				Pusher push = new Pusher(build, floor, room, viewState ,phone ,type);
				pushlist.add(push);
			}
			db.closeConn(conn, st, rs);
			ps.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pushlist;
	}
}
