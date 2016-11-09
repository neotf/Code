package org.neojo.server.entity;

public class OPType {
	private int tid;
	private String type;

	public OPType(int tid,String type){
		this.tid = tid;
		this.type = type;
	}
	public int getTid() {
		return tid;
	}
	public String getType() {
		return type;
	}
	@Override
	public String toString() {
		return type;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public void setType(String type) {
		this.type = type;
	}
}
