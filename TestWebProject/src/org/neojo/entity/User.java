package org.neojo.entity;

public class User {
	private int uid;
	private String name;
	private String username;
	private String password;
	public User(int uid, String name, String username, String password) {
		super();
		this.uid = uid;
		this.name = name;
		this.username = username;
		this.password = password;
	}
	
	public User(){
		
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
