package org.neojo.service;

import org.neojo.entity.User;

public interface UserService {

	public User GetUser(int uid);

	public User GetUser(String username);
	
	public int CheckUserStatus(int uid);
	public int CheckUserStatus(String username);
	public int CheckUserStatus(User user);
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return 
	 * @return
	 * @return
	 */
	public int Login(String username,String password);
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public int Register(User user);
}
