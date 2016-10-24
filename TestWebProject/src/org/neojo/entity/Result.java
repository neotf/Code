package org.neojo.entity;

public class Result {
	private int id;
	private String msg;
	
	public Result(int id, String msg) {
		super();
		this.id = id;
		this.msg = msg;
	}
	
	public Result(){}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
