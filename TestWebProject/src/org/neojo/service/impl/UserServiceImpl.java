package org.neojo.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.neojo.dao.mapper.UserMapper;
import org.neojo.entity.User;
import org.neojo.service.UserService;
import org.neojo.util.MD5;
import org.neojo.util.MybatisUtil;

public class UserServiceImpl implements UserService {
	private SqlSessionFactory sessionFactory = MybatisUtil.getInstance();

	@Override
	public User GetUser(int uid) {
		SqlSession session = sessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			User user = userMapper.getUserbyUid(uid);
			session.commit();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public User GetUser(String username) {
		SqlSession session = sessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			User user = userMapper.getUserbyUsername(username);
			session.commit();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public int CheckUserStatus(int uid) {
		User user = GetUser(uid);
		if (user != null) {
			return user.getStatus();
		} else {
			return 0;
		}
	}

	@Override
	public int CheckUserStatus(String username) {
		User user = GetUser(username);
		if (user != null) {
			return user.getStatus();
		} else {
			return 0;
		}
	}

	@Override
	public int CheckUserStatus(User user) {
		if (user != null) {
			return user.getStatus();
		} else {
			return 0;
		}
	}

	@Override
	public int Login(String username, String password) {
		User user = GetUser(username);
		if (user == null) {
			return 0;
		} else if (CheckUserStatus(user) > 0) {
			if(MD5.getMd5(password+"salt"+user.getUid()).equals(user.getPassword())){
				return user.getUid();
			}else{
				return -1;
			}
		} else {
			return CheckUserStatus(user);
		}
	}

}
