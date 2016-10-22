package org.neojo.test;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.neojo.dao.mapper.UserMapper;
import org.neojo.entity.User;
import org.neojo.util.MybatisUtil;

public class MybatisTest {
	private SqlSessionFactory sessionFactory = MybatisUtil.getInstance();

	@Test
	public void save() {
		SqlSession session = sessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			User user = new User();
			user.setUid(1);
			user.setName("����");
			user.setPassword("123456");
			userMapper.save(user);
			session.commit();// �ύ����
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
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
