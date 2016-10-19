package org.neojo.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBH {
	public Connection getConn() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
		Properties config = new Properties();
		config.load(new FileInputStream(new File("./config/db.properties")));
		String url = "jdbc:mysql://"+config.getProperty("ip")+"/"+config.getProperty("dbname");
		String user = config.getProperty("user");
		String password = config.getProperty("password");
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url,user,password);
		return conn;
	}
}
