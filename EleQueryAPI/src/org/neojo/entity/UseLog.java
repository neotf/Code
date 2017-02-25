package org.neojo.entity;

import com.google.gson.Gson;

public class UseLog {
	private String date;
	private float used;
	
	public UseLog(String date, float used) {
		super();
		this.date = date;
		this.used = used;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getUsed() {
		return used;
	}
	public void setUsed(float used) {
		this.used = used;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
