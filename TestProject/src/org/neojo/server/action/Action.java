package org.neojo.server.action;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.neojo.server.entity.Finance;
import org.neojo.server.entity.OPType;
import org.neojo.server.entity.Order;
import org.neojo.server.entity.OrderGoods;
import org.neojo.server.entity.User;
import org.neojo.server.entity.UserInfo;

public interface Action {
	int loginAction(String username, String password);
	User getUser(String username, String password);
	UserInfo getUserInfo(int uid, User loginuser);
	boolean checkuser(String username);
	boolean checkuser(String username,String phone);
	boolean actUser(String username);
	int createUser(UserInfo user);
	boolean changePwd(String username,String password);
	boolean updateAccount(int uid,UserInfo user);
	List<Finance> getAllFinance(int page);
	boolean addFinace(Finance fin);
	boolean changeFinanceStatus(int oid,int status);
	boolean updateFinace(Finance fin);
	//List<Order> getAllOrder(int page);
	Finance getFinancebyId(int id);
	List<Order> getOrderByStatus(int status);
	List<Order> getOrderbyYear(String year);
	List<Order> getOrderbyMonth(String month);
	List<Order> getOrderbyDay(String day);
	List<Finance> getFinancebyYear(String year);
	List<Finance> getFinancebyMonth(String month);
	List<Finance> getFinancebyDay(String day);
	List<Finance> getFinancebyType(int type);
	List<Finance> getFinancebyStatus(int status);
	Vector<OPType> queryFinStatus();
	List<Order> getOrderByType(int type);
	Vector<User> getAllUserUid();
	Vector<OPType> queryFinType();
	Vector<OPType> queryPayType();
	Vector<OPType> queryOrderType();
	Vector<OPType> queryOrderStatus();
	List<OrderGoods> getOrderGoodsbyOid(int oid);
	Order getOrderbyOid(int oid);
	int createOrder(Order od,List<OrderGoods> odgs);
	boolean changeOrderStatus(int oid,int status);
	boolean updateOrder(int oid, Order nod);
	Date[] QueryOrderDate();
	Date[] QueryFinanceDate();
}
