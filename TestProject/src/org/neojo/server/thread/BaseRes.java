package org.neojo.server.thread;

import java.util.Date;
import java.util.Map;
import java.util.Vector;

import org.neojo.server.entity.OPType;
import org.neojo.server.entity.User;

public class BaseRes {
	static boolean available = false;
	//已登录用户数据
	static User LoginUser;
	//用户数据
	static Map<Integer,User> UserListMap;
	static Vector<User> User;
	//财务类型
	static Map<Integer,OPType> FinTypeMap;
	static Vector<OPType> FinType;
	//交易类型
	static Map<Integer,OPType> PayTypeMap;
	static Vector<OPType> PayType;
	//订单类型
	static Map<Integer,OPType> OrderTypeMap;
	static Vector<OPType> OrderType;
	//订单状态
	static Map<Integer,OPType> OrderStatusMap;
	static Vector<OPType> OrderStatus;
	//财务状态
	static Map<Integer,OPType> FinStatusMap;
	static Vector<OPType> FinStatus;
	//起始时间
	static Date[] orddate;
	static Date[] findate;
	
	public static Vector<OPType> getFinStatus() {
		return FinStatus;
	}
	public static Map<Integer, OPType> getFinStatusMap() {
		return FinStatusMap;
	}
	public static Date[] getOrddate() {
		return orddate;
	}
	public static Date[] getFindate() {
		return findate;
	}
	public static Map<Integer, OPType> getOrderStatusMap() {
		return OrderStatusMap;
	}
	public static Vector<OPType> getOrderStatus() {
		return OrderStatus;
	}
	
	
	public static Map<Integer, OPType> getPayTypeMap() {
		return PayTypeMap;
	}
	public static Map<Integer, OPType> getOrderTypeMap() {
		return OrderTypeMap;
	}
	public static Vector<OPType> getOrderType() {
		return OrderType;
	}
	public static Vector<User> getUser() {
		return User;
	}
	public static Vector<OPType> getFinType() {
		return FinType;
	}
	public static Vector<OPType> getPayType() {
		return PayType;
	}
	public static boolean isAvailable() {
		return available;
	}
	public static User getLoginUser() {
		return LoginUser;
	}
	public static void setLoginUser(User loginUser) {
		LoginUser = loginUser;
	}
	public static Map<Integer, User> getUserMap() {
		return UserListMap;
	}
	public static Map<Integer, OPType> getFinTypeMap() {
		return FinTypeMap;
	}
}
