package org.neojo.server.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.neojo.server.exception.DaoException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

public class DaoBase {

	public Connection getConnection() throws DaoException {
		int i = 0;
		try {
			Properties pps = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream("config/conn.properties"));
			pps.load(in);
			Class.forName(pps.getProperty("DRIVER"));
			Connection conn = DriverManager.getConnection(pps.getProperty("URL"), pps.getProperty("USERNAME"),
					pps.getProperty("PASSWORD"));
			return conn;
		} catch (CommunicationsException e1) {
			i++;
			if (i < 10) {
				getConnection();
			} else {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return null;
	}

	protected void closeDbObject(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void closeDbObject(ResultSet rs, PreparedStatement ps, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
