package org.neojo.test;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.neojo.dao.mapper.UserMapper;
import org.neojo.entity.User;
import org.neojo.utils.MybatisUtil;

public class MybatisTest {
	private SqlSessionFactory sessionFactory = MybatisUtil.getInstance();

//	@Test
//	public void save() {
//		SqlSession session = sessionFactory.openSession();
//		try {
//			UserMapper userMapper = session.getMapper(UserMapper.class);
//			User user = new User();
//			user.setUid(1);
//			user.setName("张三");
//			user.setPassword("123456");
//			userMapper.save(user);
//			session.commit();// 提交事务
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			session.close();
//		}
//	}
//
//	@Test
//	public void update() {
//		SqlSession session = sessionFactory.openSession();
//		try {
//			UserMapper userMapper = session.getMapper(UserMapper.class);
//
//			User user = userMapper.find("1");
//			user.setName("李四");
//
//			userMapper.update(user);
//			session.commit();// 提交事务
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			session.close();
//		}
//	}

	@Test
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
