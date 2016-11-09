package org.neojo.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.neojo.server.dao.FinanceDao;
import org.neojo.server.entity.Finance;
import org.neojo.server.exception.DaoException;
import org.neojo.server.util.DaoBase;

public class FinanceDaoImpl extends DaoBase implements FinanceDao {
	private String ADD_Finance_SQL = "INSERT INTO finance (tid,oid,money,content,uid,status) VALUES(?,?,?,?,?,?)";
	private String GET_All_Finance_SQL = "select * FROM finance";
	private String GET_Finance_By_id = "select * from finance where id=?";
	private String CGT_Finance_Status = "update finance set status=? where tid=1 and oid=?";
	private String GET_Finance_By_Year = "select * from finance where year(date)=?";
	private String GET_Finance_By_Month = "Select * FROM finance Where DATE_FORMAT( date, '%Y%m' ) = ?";
	private String GET_Finance_By_Day = "Select * FROM finance Where DATE_FORMAT( date, '%Y%m%d' ) = ?";
	private String GET_Finance_By_Status_SQL ="select * FROM finance where status=?";
	private String GET_Finance_By_Type_SQL ="select * FROM finance where tid=?";
	private String UPT_Finance_By_Id = "update finance set tid=?,oid=?,money=?,uid=?,status=?,content=? where id=?";
	
	@Override
	public List<Finance> getAllFinance(int page) throws DaoException {
		List<Finance> vfae = new ArrayList<Finance>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_All_Finance_SQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				Finance fae = new Finance();
				fae.setId(rs.getInt("id"));
				fae.setDate(rs.getTimestamp("date"));
				fae.setTid(rs.getInt("tid"));
				fae.setOid(rs.getInt("oid"));
				fae.setContent(rs.getString("content"));
				fae.setMoney(rs.getDouble("money"));
				fae.setUid(rs.getInt("uid"));
				fae.setStatus(rs.getInt("status"));
				vfae.add(fae);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get finance. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return vfae;
	}
	
	@Override
	public Finance getFinancebyId(int id) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Finance fae = new Finance();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Finance_By_id);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				fae.setId(rs.getInt("id"));
				fae.setDate(rs.getTimestamp("date"));
				fae.setTid(rs.getInt("tid"));
				fae.setOid(rs.getInt("oid"));
				fae.setContent(rs.getString("content"));
				fae.setMoney(rs.getDouble("money"));
				fae.setUid(rs.getInt("uid"));
				fae.setStatus(rs.getInt("status"));
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get finance. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return fae;
	}

	@Override
	public boolean addFinace(Finance fin) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(ADD_Finance_SQL);
			ps.setInt(1, fin.getTid());
			if(fin.getOid()==0){
				ps.setNull(2, Types.INTEGER);
			}else{
			ps.setInt(2, fin.getOid());}
			ps.setDouble(3, fin.getMoney());
			ps.setString(4, fin.getContent());
			ps.setInt(5, fin.getUid());
			ps.setInt(6, 1);
			if (ps.executeUpdate() > 0)
				return true;
		} catch (Exception e) {
			throw new DaoException("Erorr add finance. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return false;
	}

	@Override
	public boolean changeFinanceStatus(int oid, int status) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(CGT_Finance_Status);
			ps.setInt(1, status);
			ps.setInt(2, oid);
			if (ps.executeUpdate() == 1)
				return true;
		} catch (Exception e) {
			throw new DaoException("Erorr can not update Finance. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return false;
	}

	@Override
	public List<Finance> getFinancebyYear(String year) throws DaoException {
		List<Finance> vfae = new ArrayList<Finance>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Finance_By_Year);
			ps.setString(1, year);
			rs = ps.executeQuery();
			while (rs.next()) {
				Finance fae = new Finance();
				fae.setId(rs.getInt("id"));
				fae.setDate(rs.getTimestamp("date"));
				fae.setTid(rs.getInt("tid"));
				fae.setOid(rs.getInt("oid"));
				fae.setMoney(rs.getDouble("money"));
				fae.setContent(rs.getString("content"));
				fae.setUid(rs.getInt("uid"));
				fae.setStatus(rs.getInt("status"));
				vfae.add(fae);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get finance. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return vfae;
	}

	@Override
	public List<Finance> getFinancebyMonth(String month) throws DaoException {
		List<Finance> vfae = new ArrayList<Finance>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Finance_By_Month);
			ps.setString(1, month);
			rs = ps.executeQuery();
			while (rs.next()) {
				Finance fae = new Finance();
				fae.setId(rs.getInt("id"));
				fae.setDate(rs.getTimestamp("date"));
				fae.setTid(rs.getInt("tid"));
				fae.setOid(rs.getInt("oid"));
				fae.setMoney(rs.getDouble("money"));
				fae.setContent(rs.getString("content"));
				fae.setUid(rs.getInt("uid"));
				fae.setStatus(rs.getInt("status"));
				vfae.add(fae);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get finance. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return vfae;
	}

	@Override
	public List<Finance> getFinancebyDay(String day) throws DaoException {
		List<Finance> vfae = new ArrayList<Finance>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Finance_By_Day);
			ps.setString(1, day);
			rs = ps.executeQuery();
			while (rs.next()) {
				Finance fae = new Finance();
				fae.setId(rs.getInt("id"));
				fae.setDate(rs.getTimestamp("date"));
				fae.setTid(rs.getInt("tid"));
				fae.setOid(rs.getInt("oid"));
				fae.setMoney(rs.getDouble("money"));
				fae.setContent(rs.getString("content"));
				fae.setUid(rs.getInt("uid"));
				fae.setStatus(rs.getInt("status"));
				vfae.add(fae);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get finance. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return vfae;
	}

	@Override
	public List<Finance> getFinancebyType(int type) throws DaoException {
		List<Finance> vfae = new ArrayList<Finance>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Finance_By_Type_SQL);
			ps.setInt(1, type);
			rs = ps.executeQuery();
			while (rs.next()) {
				Finance fae = new Finance();
				fae.setId(rs.getInt("id"));
				fae.setDate(rs.getTimestamp("date"));
				fae.setTid(rs.getInt("tid"));
				fae.setOid(rs.getInt("oid"));
				fae.setMoney(rs.getDouble("money"));
				fae.setContent(rs.getString("content"));
				fae.setUid(rs.getInt("uid"));
				fae.setStatus(rs.getInt("status"));
				vfae.add(fae);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get finance. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return vfae;
	}

	@Override
	public List<Finance> getFinancebyStatus(int status) throws DaoException {
		List<Finance> vfae = new ArrayList<Finance>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Finance_By_Status_SQL);
			ps.setInt(1, status);
			rs = ps.executeQuery();
			while (rs.next()) {
				Finance fae = new Finance();
				fae.setId(rs.getInt("id"));
				fae.setDate(rs.getTimestamp("date"));
				fae.setTid(rs.getInt("tid"));
				fae.setOid(rs.getInt("oid"));
				fae.setMoney(rs.getDouble("money"));
				fae.setContent(rs.getString("content"));
				fae.setUid(rs.getInt("uid"));
				fae.setStatus(rs.getInt("status"));
				vfae.add(fae);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get finance. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return vfae;
	}

	@Override
	public boolean updateFinace(Finance fin) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(UPT_Finance_By_Id);
			ps.setInt(1, fin.getTid());
			ps.setInt(2, fin.getOid());
			ps.setDouble(3, fin.getMoney());
			ps.setInt(4, fin.getUid());
			ps.setInt(5, fin.getStatus());
			ps.setString(6, fin.getContent());
			ps.setInt(7, fin.getId());
			if (ps.executeUpdate() == 1)
				return true;
		} catch (Exception e) {
			throw new DaoException("Erorr can not update Finance. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return false;
	}

}
