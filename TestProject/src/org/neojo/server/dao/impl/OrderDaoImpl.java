package org.neojo.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.neojo.server.dao.OrderDao;
import org.neojo.server.entity.Order;
import org.neojo.server.entity.OrderGoods;
import org.neojo.server.exception.DaoException;
import org.neojo.server.util.DaoBase;

import com.mysql.jdbc.Statement;

public class OrderDaoImpl extends DaoBase implements OrderDao {
	private String GET_All_Order_SQL = "select * FROM orders";
	private String GET_Order_By_Year = "select * from orders where year(date)=?";
	private String GET_Order_By_Month = "Select * FROM orders Where DATE_FORMAT( date, '%Y%m' ) = ?";
	private String GET_Order_By_Day = "Select * FROM orders Where DATE_FORMAT( date, '%Y%m%d' ) = ?";
	private String GET_Order_By_Status_SQL ="select * FROM orders where status=?";
	private String GET_Order_By_Type_SQL ="select * FROM orders where otype=?";
	private String GET_OrderGoods_By_Oid_SQL = "select * from ordergoods where oid = ?";
	private String GET_Order_By_Oid_SQL = "select * from orders where id = ?";
	private String CRE_Order_SQL = "INSERT INTO orders (otype,ptype,tsn,uid,iuid,total,profit,bonus,status,custom,customid) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	private String CRE_Order_Goods_SQL = "INSERT INTO ordergoods (oid,name,num,price,total) VALUES(?,?,?,?,?)";
	private String UPT_Order_SQL = "update orders set otype=?,ptype=?,tsn=?,uid=?,iuid=?,total=?,profit=?,bonus=?,status=?,custom=?,customid=? where id=?";
	private String CGT_Order_Status = "update orders set status=? where id=?";

	@Override
	public List<Order> getAllOrder(int page) throws DaoException {
		List<Order> odl = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_All_Order_SQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order od = new Order();
				od.setId(rs.getInt("id"));
				od.setOtype(rs.getInt("otype"));
				od.setPtype(rs.getInt("ptype"));
				od.setTsn(rs.getString("tsn"));
				od.setUid(rs.getInt("uid"));
				od.setIuid(rs.getInt("iuid"));
				od.setTotal(rs.getDouble("total"));
				od.setProfit(rs.getDouble("profit"));
				od.setBonus(rs.getDouble("bonus"));
				od.setStatus(rs.getInt("status"));
				od.setDate(rs.getTimestamp("date"));
				od.setCustom(rs.getString("custom"));
				od.setCustomid(rs.getInt("customid"));
				odl.add(od);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get order. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return odl;
	}

	@Override
	public boolean delOrderById(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("delete xxx from xxx where id=?");
			ps.setInt(1, 5);
			ps.execute();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<OrderGoods> getOrderGoodsbyOid(int oid) throws DaoException {
		List<OrderGoods> ogl = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_OrderGoods_By_Oid_SQL);
			ps.setInt(1, oid);
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderGoods og = new OrderGoods();
				og.setOid(rs.getInt("oid"));
				og.setName(rs.getString("name"));
				og.setNum(rs.getInt("num"));
				og.setPrice(rs.getDouble("price"));
				og.setTotal(rs.getDouble("total"));
				ogl.add(og);
			}

		} catch (Exception e) {
			throw new DaoException("Erorr can not get OrderGoods. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return ogl;
	}

	@Override
	public Order getOrderbyOid(int oid) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Order od = new Order();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Order_By_Oid_SQL);
			ps.setInt(1, oid);
			rs = ps.executeQuery();
			rs.last();
			if (rs.getRow() != 1)
				return null;
			od.setId(rs.getInt("id"));
			od.setOtype(rs.getInt("otype"));
			od.setPtype(rs.getInt("ptype"));
			od.setTsn(rs.getString("tsn"));
			od.setUid(rs.getInt("uid"));
			od.setIuid(rs.getInt("iuid"));
			od.setTotal(rs.getDouble("total"));
			od.setProfit(rs.getDouble("profit"));
			od.setBonus(rs.getDouble("bonus"));
			od.setStatus(rs.getInt("status"));
			od.setDate(rs.getTimestamp("date"));
			od.setCustom(rs.getString("custom"));
			od.setCustomid(rs.getInt("customid"));
		} catch (Exception e) {
			throw new DaoException("Erorr can not get Order. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return od;
	}

	@Override
	public int createOrder(Order od, List<OrderGoods> odgs) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		int id = -1; 
		try {
			conn = getConnection();
			ps = conn.prepareStatement(CRE_Order_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, od.getOtype());
			ps.setInt(2, od.getPtype());
			ps.setString(3, od.getTsn());
			ps.setInt(4, od.getUid());
			ps.setInt(5, od.getIuid());
			ps.setDouble(6, od.getTotal());
			ps.setDouble(7, od.getProfit());
			ps.setDouble(8, od.getBonus());
			ps.setInt(9, od.getStatus());
			ps.setString(10, od.getCustom());
			ps.setInt(11, od.getCustomid());
			int psstatus1 = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
				ps2 = conn.prepareStatement(CRE_Order_Goods_SQL);
				for (int i = 0; i < odgs.size(); i++) {
					ps2.setInt(1, id);
					ps2.setString(2, odgs.get(i).getName());
					ps2.setInt(3, odgs.get(i).getNum());
					ps2.setDouble(4, odgs.get(i).getPrice());
					ps2.setDouble(5, odgs.get(i).getTotal());
					if (ps2.executeUpdate() != 1)
						return -1;
				}
			}
			if (psstatus1 == 1)
				return id;
		} catch (Exception e) {
			throw new DaoException("Erorr can not creat Order. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return -1;
	}

	@Override
	public boolean updateOrder(int oid, Order nod) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(UPT_Order_SQL);
			ps.setInt(1, nod.getOtype());
			ps.setInt(2, nod.getPtype());
			ps.setString(3, nod.getTsn());
			ps.setInt(4, nod.getUid());
			ps.setInt(5, nod.getIuid());
			ps.setDouble(6, nod.getTotal());
			ps.setDouble(7, nod.getProfit());
			ps.setDouble(8, nod.getBonus());
			ps.setInt(9, nod.getStatus());
			ps.setString(10, nod.getCustom());
			ps.setInt(11, nod.getCustomid());
			ps.setInt(12, oid);
			if (ps.executeUpdate() == 1)
				return true;
		} catch (Exception e) {
			throw new DaoException("Erorr can not update Order. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return false;
	}

	@Override
	public boolean changeOrderStatus(int oid,int status) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(CGT_Order_Status);
			ps.setInt(1, status);
			ps.setInt(2, oid);
			if (ps.executeUpdate() == 1)
				return true;
		} catch (Exception e) {
			throw new DaoException("Erorr can not update Order. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return false;
	}

	@Override
	public List<Order> getOrderByStatus(int status) throws DaoException {
		List<Order> odl = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Order_By_Status_SQL);
			ps.setInt(1, status);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order od = new Order();
				od.setId(rs.getInt("id"));
				od.setOtype(rs.getInt("otype"));
				od.setPtype(rs.getInt("ptype"));
				od.setTsn(rs.getString("tsn"));
				od.setUid(rs.getInt("uid"));
				od.setIuid(rs.getInt("iuid"));
				od.setTotal(rs.getDouble("total"));
				od.setProfit(rs.getDouble("profit"));
				od.setBonus(rs.getDouble("bonus"));
				od.setStatus(rs.getInt("status"));
				od.setDate(rs.getTimestamp("date"));
				od.setCustom(rs.getString("custom"));
				od.setCustomid(rs.getInt("customid"));
				odl.add(od);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get order. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return odl;
	}

	@Override
	public List<Order> getOrderbyYear(String year) throws DaoException {
		List<Order> odl = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Order_By_Year);
			ps.setString(1, year);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order od = new Order();
				od.setId(rs.getInt("id"));
				od.setOtype(rs.getInt("otype"));
				od.setPtype(rs.getInt("ptype"));
				od.setTsn(rs.getString("tsn"));
				od.setUid(rs.getInt("uid"));
				od.setIuid(rs.getInt("iuid"));
				od.setTotal(rs.getDouble("total"));
				od.setProfit(rs.getDouble("profit"));
				od.setBonus(rs.getDouble("bonus"));
				od.setStatus(rs.getInt("status"));
				od.setDate(rs.getTimestamp("date"));
				od.setCustom(rs.getString("custom"));
				od.setCustomid(rs.getInt("customid"));
				odl.add(od);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get order. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return odl;
	}

	@Override
	public List<Order> getOrderbyMonth(String month) throws DaoException {
		List<Order> odl = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Order_By_Month);
			ps.setString(1, month);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order od = new Order();
				od.setId(rs.getInt("id"));
				od.setOtype(rs.getInt("otype"));
				od.setPtype(rs.getInt("ptype"));
				od.setTsn(rs.getString("tsn"));
				od.setUid(rs.getInt("uid"));
				od.setIuid(rs.getInt("iuid"));
				od.setTotal(rs.getDouble("total"));
				od.setProfit(rs.getDouble("profit"));
				od.setBonus(rs.getDouble("bonus"));
				od.setStatus(rs.getInt("status"));
				od.setDate(rs.getTimestamp("date"));
				od.setCustom(rs.getString("custom"));
				od.setCustomid(rs.getInt("customid"));
				odl.add(od);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get order. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return odl;
	}

	@Override
	public List<Order> getOrderbyDay(String day) throws DaoException {
		List<Order> odl = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Order_By_Day);
			ps.setString(1, day);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order od = new Order();
				od.setId(rs.getInt("id"));
				od.setOtype(rs.getInt("otype"));
				od.setPtype(rs.getInt("ptype"));
				od.setTsn(rs.getString("tsn"));
				od.setUid(rs.getInt("uid"));
				od.setIuid(rs.getInt("iuid"));
				od.setTotal(rs.getDouble("total"));
				od.setProfit(rs.getDouble("profit"));
				od.setBonus(rs.getDouble("bonus"));
				od.setStatus(rs.getInt("status"));
				od.setDate(rs.getTimestamp("date"));
				od.setCustom(rs.getString("custom"));
				od.setCustomid(rs.getInt("customid"));
				odl.add(od);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get order. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return odl;
	}

	@Override
	public List<Order> getOrderByType(int type) throws DaoException {
		List<Order> odl = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_Order_By_Type_SQL);
			ps.setInt(1, type);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order od = new Order();
				od.setId(rs.getInt("id"));
				od.setOtype(rs.getInt("otype"));
				od.setPtype(rs.getInt("ptype"));
				od.setTsn(rs.getString("tsn"));
				od.setUid(rs.getInt("uid"));
				od.setIuid(rs.getInt("iuid"));
				od.setTotal(rs.getDouble("total"));
				od.setProfit(rs.getDouble("profit"));
				od.setBonus(rs.getDouble("bonus"));
				od.setStatus(rs.getInt("status"));
				od.setDate(rs.getTimestamp("date"));
				od.setCustom(rs.getString("custom"));
				od.setCustomid(rs.getInt("customid"));
				odl.add(od);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get order. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return odl;
	}

}
