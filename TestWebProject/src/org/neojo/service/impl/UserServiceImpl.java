package org.neojo.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.neojo.dao.mapper.UserMapper;
import org.neojo.entity.User;
import org.neojo.util.MybatisUtil;

public class UserServiceImpl {
private SqlSessionFactory sessionFactory = MybatisUtil.getInstance();
	
	public void find() {
		SqlSession session = sessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);

			User user = userMapper.find("1");

			System.out.println(
					"id : " + user.getUid() + ", name : " + user.getName() + ", password : " + user.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
