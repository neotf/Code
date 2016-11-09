package org.neojo.server.dao;

import java.util.Vector;

import org.neojo.server.entity.User;
import org.neojo.server.entity.UserInfo;
import org.neojo.server.exception.DaoException;

public interface UserDao {
	//查询账户
	boolean checkUser(String username) throws DaoException;
	boolean checkUser(String username,String phone) throws DaoException;
	boolean changePwd(String username,String password) throws DaoException;
	boolean actUser(String username) throws DaoException;
	User GetUserStatusByUsername(String username,String password) throws DaoException;
	UserInfo queryUserInfo(String username,String password) throws DaoException;
	Vector<User> queryAllUserUid() throws DaoException;
	public UserInfo queryUserInfoByUid(int uid) throws DaoException;
	//更新账户
	boolean updateAccount(int uid,UserInfo user) throws DaoException;
	//
	int createUser(UserInfo user) throws DaoException;

}
