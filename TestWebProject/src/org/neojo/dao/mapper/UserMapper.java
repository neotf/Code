package org.neojo.dao.mapper;

import org.neojo.entity.User;

public interface UserMapper {
	   public void save(User user);
	   public void update(User user);
	   public User getUserbyUid(int uid);
	   public User getUserbyUsername(String username);
	   public void delete(String uid);
}
