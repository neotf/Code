package org.neojo.server.entity;

import java.util.Date;

public class Order {
	private int id;
	private int otype;
	private int ptype;
	private String tsn;
	private int uid;
	private int iuid;
	private double total;
	private double profit;
	private double bonus;
	private int status;
	private Date date;
	private String custom;
	private int customid;
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
	public int getCustomid() {
		return customid;
	}
	public void setCustomid(int customid) {
		this.customid = customid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getOtype() {
		return otype;
	}
	public void setOtype(int otype) {
		this.otype = otype;
	}
	public int getPtype() {
		return ptype;
	}
	public void setPtype(int ptype) {
		this.ptype = ptype;
	}
	public String getTsn() {
		return tsn;
	}
	public void setTsn(String tsn) {
		this.tsn = tsn;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getIuid() {
		return iuid;
	}
	public void setIuid(int iuid) {
		this.iuid = iuid;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getBonus() {
		return bonus;
	}
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
}
