package org.neojo.entity;

public class Result {
	private int code;
	private String msg;
	private Electricity data;
	
	public Result(int code ,String msg, Electricity data) {
		super();
		this.code = code;
		this.data = data;
		this.msg = msg;
	}
	
	public Result(int code ,String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public Result(){}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Electricity getData() {
		return data;
	}
	public void setData(Electricity data) {
		this.data = data;
	}
}
