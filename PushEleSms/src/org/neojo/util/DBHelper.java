package org.neojo.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DBHelper {
	public Connection getConn() throws ClassNotFoundException, SQLException, IOException {
		Properties pps = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream("config/conn.properties"));
		pps.load(in);
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://"+pps.getProperty("IP")+"/"+pps.getProperty("DBNAME"), pps.getProperty("USERNAME"),pps.getProperty("PASSWORD"));
	}

	public boolean closeConn(Connection conn, Statement st, ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
