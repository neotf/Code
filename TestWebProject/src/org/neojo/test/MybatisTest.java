package org.neojo.test;

import org.junit.Test;
import org.neojo.entity.User;
import org.neojo.service.UserService;
import org.neojo.service.impl.UserServiceImpl;

public class MybatisTest {
	private UserService us = new UserServiceImpl();
	@Test
	public void test(){
		System.out.println(us.CheckUserStatus(1));
		System.out.println(us.CheckUserStatus("qweraa"));
		System.out.println(us.CheckUserStatus(new User(2,"",0, "","", null, null, 0, 0, null, null, null, 0)));;
		
	}
//
//	@Test
//	public void save() {
//		SqlSession session = sessionFactory.openSession();
//		try {
//			UserMapper userMapper = session.getMapper(UserMapper.class);
//			User user = new User();
//			user.setUid(1);
//			user.setName("����");
//			user.setPassword("123456");
//			userMapper.save(user);
//			session.commit();// �ύ����
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
//			user.setName("����");
//
//			userMapper.update(user);
//			session.commit();// �ύ����
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			session.close();
//		}
//	}

	
	

}
