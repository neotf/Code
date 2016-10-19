package org.neojo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.neojo.entity.User;

public class action {
	DBH db = new DBH();
	public int checkUser(String username){
		try {
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement("select status from users where username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt("status");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public void checkPassword(String username,String password){
		
	}
	public User getUser(int uid){
		User user = new User();
		try {
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement("select status from users where username=?");
			ps.setString(1, "");
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
