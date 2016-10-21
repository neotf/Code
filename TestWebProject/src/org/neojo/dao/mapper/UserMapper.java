package org.neojo.dao.mapper;

import org.neojo.entity.User;

public interface UserMapper {
	   public void save(User user);
	   public void update(User user);
	   public User find(String id);
	   public void delete(String id);
}
