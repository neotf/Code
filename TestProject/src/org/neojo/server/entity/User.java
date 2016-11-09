package org.neojo.server.entity;

public class User {
	private int uid;
	private String name;
	private int deptno;
	private int job;
	public int getJob() {
		return job;
	}
	public void setJob(int job) {
		this.job = job;
	}
	private int status; 
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
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
	@Override
	public String toString() {
		return name;
	}
}
