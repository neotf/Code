package org.neojo.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBH {
	public Connection getConn() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
		Properties config = new Properties();
		config.load(this.getClass().getResourceAsStream("db.properties"));
		String url = "jdbc:mysql://"+config.getProperty("ip")+"/"+config.getProperty("dbname");
		String user = config.getProperty("user");
		String password = config.getProperty("password");
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url,user,password);
		return conn;
	}
	public void close(Connection conn,Statement st,ResultSet rs) throws SQLException{
		if(conn!=null){
			conn.close();
		}
		if(st!=null){
			st.close();
		}
		if(rs!=null){
			rs.close();
		}
	}
	public void close(Connection conn,PreparedStatement ps,ResultSet rs) throws SQLException{
		if(conn!=null){
			conn.close();
		}
		if(ps!=null){
			ps.close();
		}
		if(rs!=null){
			rs.close();
		}
	}
}
