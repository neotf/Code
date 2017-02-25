package org.neojo.entity;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BuyLog {
	private Date date;
	private float buy;
	private float pay;
	private int type;
	private String opt;
	
	
	public BuyLog(Date date, float buy, float pay, int type, String opt) {
		super();
		this.date = date;
		this.buy = buy;
		this.pay = pay;
		this.type = type;
		this.opt = opt;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getBuy() {
		return buy;
	}
	public void setBuy(float buy) {
		this.buy = buy;
	}
	public float getPay() {
		return pay;
	}
	public void setPay(float pay) {
		this.pay = pay;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	
	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.toJson(this);
	}
	
}
