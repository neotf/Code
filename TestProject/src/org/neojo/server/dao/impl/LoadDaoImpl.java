package org.neojo.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

import org.neojo.server.dao.LoadDao;
import org.neojo.server.entity.OPType;
import org.neojo.server.exception.DaoException;
import org.neojo.server.util.DaoBase;

public class LoadDaoImpl extends DaoBase implements LoadDao {
	private String Query_Type_SQL = "select tid,type FROM ";
	private String Query_Date_SQL = "SELECT min(date) as min ,max(date) as max from "; 
	@Override
	public Vector<OPType> QueryType(String type) throws DaoException {
		Vector<OPType> tmp = new Vector<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(Query_Type_SQL+type);
			rs = ps.executeQuery();
			while (rs.next()) {
				OPType tp = new OPType(rs.getInt("tid"),rs.getString("type"));
				tmp.add(tp);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get "+type+ e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return tmp;
	}
	@Override
	public Date[] QueryDate(String type) throws DaoException {
		Date[] date = new Date[2];
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(Query_Date_SQL+type);
			rs = ps.executeQuery();
			if(rs.next()) {
				date[0]=rs.getDate("min");
				date[1]=rs.getDate("max");
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get "+type+ e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return date;
	}

}
