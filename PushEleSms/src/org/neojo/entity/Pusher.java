package org.neojo.entity;

public class Pusher {
	private int build;
	private int floor;
	private int room;
	private String ViewState;
	private String phone;
	private String type;
	public Pusher(){
		
	}
	public Pusher(int build, int floor, int room, String viewState,String phone,String type) {
		super();
		this.build = build;
		this.floor = floor;
		this.room = room;
		this.ViewState = viewState;
		this.phone = phone;
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getBuild() {
		return build;
	}
	public void setBuild(int build) {
		this.build = build;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public String getViewState() {
		return ViewState;
	}
	public void setViewState(String viewState) {
		ViewState = viewState;
	}
	
	
}
