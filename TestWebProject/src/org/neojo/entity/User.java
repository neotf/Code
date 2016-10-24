package org.neojo.entity;

import java.util.Date;

public class User {
	private int uid;
	private String name;
	private int sex;
	private String phone;
	private String email;
	private String qq;
	private String idno;
	private int deptno;
	private int job;
	private Date edtime;
	private String username;
	private String password;
	private int status;
	
	public User(int uid, String name, int sex, String phone, String email, String qq, String idno, int deptno, int job,
			Date edtime, String username, String password, int status) {
		super();
		this.uid = uid;
		this.name = name;
		this.sex = sex;
		this.phone = phone;
		this.email = email;
		this.qq = qq;
		this.idno = idno;
		this.deptno = deptno;
		this.job = job;
		this.edtime = edtime;
		this.username = username;
		this.password = password;
		this.status = status;
	}

	public User() {}

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

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public int getJob() {
		return job;
	}

	public void setJob(int job) {
		this.job = job;
	}

	public Date getEdtime() {
		return edtime;
	}

	public void setEdtime(Date edtime) {
		this.edtime = edtime;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	};
	
	
	

}
