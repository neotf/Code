package org.neojo.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.neojo.server.dao.UserDao;
import org.neojo.server.entity.User;
import org.neojo.server.entity.UserInfo;
import org.neojo.server.exception.DaoException;
import org.neojo.server.util.DaoBase;
import org.neojo.server.util.MD5;

import com.mysql.jdbc.Statement;

public class UserDaoImpl extends DaoBase implements UserDao {
	private String Check_User_By_Username = "SELECT username FROM users where username=?";
	private String Check_User_By_Phone = "SELECT username FROM users where username=? and phone=?";
	private String CRE_User_SQL = "INSERT INTO users (name,sex,phone,email,qq,idno,deptno,job,stuid,username,password,status) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
	private String UPT_User_SQL = "update users set name=?,sex=?,phone=?,email=?,qq=?,idno=?,deptno=?,job=?,stuid=?,username=?,status=? where uid=?";
	private String GET_User_SQL = "SELECT uid,name,deptno,job,status FROM users where username=? and password=?";
	private String CGE_User_Pwd_SQL = "update users set password=? where username=?";
	private String ACT_User_SQL = "update users set status=1 where username=?";
	private String Query_UserInfo_SQL = "SELECT * FROM users where username=? and password=?";
	private String Query_UserInfo_By_Uid_SQL = "SELECT * FROM users where uid=?";
	private String GET_All_User_Uid_SQL = "SELECT uid,name,deptno,job,status FROM users";

	@Override
	public boolean checkUser(String username) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(Check_User_By_Username);
			ps.setString(1, username);
			rs = ps.executeQuery();
			rs.last();
			if (rs.getRow() != 1)
				return false;
			return true;
		} catch (Exception e) {
			throw new DaoException("LoginErorr can not get user. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
	}

	@Override
	public User GetUserStatusByUsername(String username, String password) throws DaoException {
		User user = new User();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_User_SQL);
			ps.setString(1, username);
			ps.setString(2, MD5.getMd5(password));
			rs = ps.executeQuery();
			rs.last();
			if (rs.getRow() != 1)
				return null;
			user.setUid(rs.getInt("uid"));
			user.setName(rs.getString("name"));
			user.setDeptno(rs.getInt("deptno"));
			user.setJob(rs.getInt("job"));
			user.setStatus(rs.getInt("status"));
		} catch (Exception e) {
			throw new DaoException("LoginErorr can not get user. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return user;
	}

	@Override
	public UserInfo queryUserInfo(String username, String password) throws DaoException {
		UserInfo user = new UserInfo();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(Query_UserInfo_SQL);
			ps.setString(1, username);
			ps.setString(2, MD5.getMd5(password));
			rs = ps.executeQuery();
			rs.last();
			if (rs.getRow() != 1)
				return null;
			user.setUid(rs.getInt("uid"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));
			user.setSex(rs.getInt("sex"));
			user.setPhone(rs.getString("phone"));
			user.setEmail(rs.getString("email"));
			user.setQq(rs.getString("qq"));
			user.setDeptno(rs.getInt("deptno"));
			user.setJob(rs.getInt("job"));
			user.setEdtime(rs.getDate("edtime"));
			user.setIdno(rs.getString("idno"));
			user.setStuid(rs.getString("stuid"));
			user.setStatus(rs.getInt("status"));
		} catch (Exception e) {
			throw new DaoException("LoginErorr can not get user. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}

		return user;
	}

	@Override
	public Vector<User> queryAllUserUid() throws DaoException {
		Vector<User> users = new Vector<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(GET_All_User_Uid_SQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUid(rs.getInt("uid"));
				user.setName(rs.getString("name"));
				user.setDeptno(rs.getInt("deptno"));
				user.setJob(rs.getInt("job"));
				user.setStatus(rs.getInt("status"));
				users.add(user);
			}
		} catch (Exception e) {
			throw new DaoException("Erorr get user. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return users;
	}

	@Override
	public UserInfo queryUserInfoByUid(int uid) throws DaoException {
		UserInfo user = new UserInfo();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(Query_UserInfo_By_Uid_SQL);
			ps.setInt(1, uid);
			rs = ps.executeQuery();
			rs.last();
			if (rs.getRow() != 1)
				return null;
			user.setUid(rs.getInt("uid"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));
			user.setSex(rs.getInt("sex"));
			user.setPhone(rs.getString("phone"));
			user.setEmail(rs.getString("email"));
			user.setQq(rs.getString("qq"));
			user.setDeptno(rs.getInt("deptno"));
			user.setJob(rs.getInt("job"));
			user.setEdtime(rs.getDate("edtime"));
			user.setIdno(rs.getString("idno"));
			user.setStuid(rs.getString("stuid"));
			user.setStatus(rs.getInt("status"));
		} catch (Exception e) {
			throw new DaoException("Erorr can not get user. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}

		return user;

	}

	@Override
	public int createUser(UserInfo user) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = -1;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(CRE_User_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getName());
			ps.setInt(2, user.getSex());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getQq());
			ps.setString(6, user.getIdno());
			ps.setInt(7, user.getDeptno());
			ps.setInt(8, user.getJob());
			ps.setString(9, user.getStuid());
			ps.setString(10, user.getUsername());
			ps.setString(11, user.getPassword());
			ps.setInt(12, user.getStatus());
			int psstatus = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			if (psstatus == 1)
				return id;
		} catch (Exception e) {
			throw new DaoException("Erorr can not creat User. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return -1;
	}
	
	@Override
	public boolean updateAccount(int uid,UserInfo user) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(UPT_User_SQL);
			ps.setString(1, user.getName());
			ps.setInt(2, user.getSex());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getQq());
			ps.setString(6, user.getIdno());
			ps.setInt(7, user.getDeptno());
			ps.setInt(8, user.getJob());
			ps.setString(9, user.getStuid());
			ps.setString(10, user.getUsername());
			ps.setInt(11, user.getStatus());
			ps.setInt(12, uid);
			int psstatus = ps.executeUpdate();
			if (psstatus == 1)
				return true;
		} catch (Exception e) {
			throw new DaoException("Erorr can not update User. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return false;
	}

	@Override
	public boolean checkUser(String username, String phone) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(Check_User_By_Phone);
			ps.setString(1, username);
			ps.setString(2, phone);
			rs = ps.executeQuery();
			rs.last();
			if (rs.getRow() != 1)
				return false;
			return true;
		} catch (Exception e) {
			throw new DaoException("Erorr can not get user. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
	}

	@Override
	public boolean changePwd(String username, String password) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(CGE_User_Pwd_SQL);
			ps.setString(1, password);
			ps.setString(2, username);
			int psstatus = ps.executeUpdate();
			if (psstatus == 1)
				return true;
		} catch (Exception e) {
			throw new DaoException("Erorr can not Change User Pwd. " + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return false;
	}

	@Override
	public boolean actUser(String username) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(ACT_User_SQL);
			ps.setString(1, username);
			int psstatus = ps.executeUpdate();
			if (psstatus == 1)
				return true;
		} catch (Exception e) {
			throw new DaoException("Erorr can not Act User" + e.getMessage());
		} finally {
			closeDbObject(rs, ps, conn);
		}
		return false;
	}


}
