package org.neojo.server.biz;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.neojo.server.entity.Finance;
import org.neojo.server.entity.OPType;
import org.neojo.server.entity.Order;
import org.neojo.server.entity.OrderGoods;
import org.neojo.server.entity.User;
import org.neojo.server.entity.UserInfo;
import org.neojo.server.exception.DaoException;

public interface Biz {
	int login(String username, String password) throws DaoException ;
	User getUser(String username, String password) throws DaoException;
	UserInfo getUserInfo(int uid,User loginuser) throws DaoException;
	boolean checkuser(String username) throws DaoException;
	boolean checkuser(String username,String phone) throws DaoException;
	boolean actUser(String username) throws DaoException;
	int createUser(UserInfo user) throws DaoException;
	boolean changePwd(String username,String password) throws DaoException;
	boolean updateAccount(int uid,UserInfo user) throws DaoException;
	List<Finance> getAllFinance(int page) throws DaoException;
	boolean addFinace(Finance fin) throws DaoException;
	boolean changeFinanceStatus(int oid,int status) throws DaoException;
	boolean updateFinace(Finance fin) throws DaoException;
	Finance getFinancebyId(int id) throws DaoException;
	List<Order> getAllOrder(int page) throws DaoException;
	List<Order> getOrderByStatus(int status) throws DaoException;
	List<Order> getOrderbyYear(String year) throws DaoException;
	List<Order> getOrderbyMonth(String month) throws DaoException;
	List<Order> getOrderbyDay(String day) throws DaoException;
	List<Finance> getFinancebyYear(String year) throws DaoException;
	List<Finance> getFinancebyMonth(String month) throws DaoException;
	List<Finance> getFinancebyDay(String day) throws DaoException;
	List<Finance> getFinancebyType(int type) throws DaoException;
	List<Finance> getFinancebyStatus(int status) throws DaoException;
	List<Order> getOrderByType(int type) throws DaoException;
	Vector<User> getAllUserUid() throws DaoException;
	Vector<OPType> queryType(String type) throws DaoException;
	List<OrderGoods> getOrderGoodsbyOid(int oid) throws DaoException;
	Order getOrderbyOid(int oid) throws DaoException;
	int createOrder(Order od,List<OrderGoods> odgs) throws DaoException;
	boolean changeOrderStatus(int oid,int status) throws DaoException;
	boolean updateOrder(int oid, Order nod) throws DaoException;
	Date[] QueryDate(String type) throws DaoException;
}
