package org.neojo.server.biz.impl;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.neojo.server.biz.Biz;
import org.neojo.server.dao.FinanceDao;
import org.neojo.server.dao.LoadDao;
import org.neojo.server.dao.OrderDao;
import org.neojo.server.dao.UserDao;
import org.neojo.server.entity.Finance;
import org.neojo.server.entity.OPType;
import org.neojo.server.entity.Order;
import org.neojo.server.entity.OrderGoods;
import org.neojo.server.entity.User;
import org.neojo.server.entity.UserInfo;
import org.neojo.server.exception.DaoException;
import org.neojo.server.factory.BeanFactory;

public class BizImpl implements Biz {
	private LoadDao ldb = BeanFactory.getLoadInstance("LoadDaoImpl");
	private UserDao udb = BeanFactory.getUserInstance("UserDaoImpl");
	private FinanceDao faedb = BeanFactory.getFinanceInstance("FinanceDaoImpl");
	private OrderDao oddb = BeanFactory.getOrderInstance("OrderDaoImpl");

	@Override
	public int login(String username, String password) throws DaoException {
		if (username == null || password == null)
			return 0;
		if (udb.checkUser(username)) {
			User user = udb.GetUserStatusByUsername(username, password);
			if (user != null)
				return user.getStatus();
		} else {
			return -1;
		}
		return -2;
	}

	@Override
	public User getUser(String username, String password) throws DaoException {
		if (username == null || password == null)
			return null;
		if (udb.checkUser(username)) {
			User user = udb.GetUserStatusByUsername(username, password);
			if (user != null)
				return user;
		} else {
			return null;
		}
		return null;
	}

	@Override
	public List<Finance> getAllFinance(int page) throws DaoException {
		return faedb.getAllFinance(page);
	}

	@Override
	public Vector<User> getAllUserUid() throws DaoException {
		return udb.queryAllUserUid();
	}

	@Override
	public Vector<OPType> queryType(String type) throws DaoException {
		return ldb.QueryType(type);
	}

	@Override
	public List<OrderGoods> getOrderGoodsbyOid(int oid) throws DaoException {
		return oddb.getOrderGoodsbyOid(oid);
	}

	@Override
	public Order getOrderbyOid(int oid) throws DaoException {
		return oddb.getOrderbyOid(oid);
	}

	@Override
	public List<Order> getAllOrder(int page) throws DaoException {
		return oddb.getAllOrder(page);
	}

	@Override
	public int createOrder(Order od, List<OrderGoods> odgs) throws DaoException {
		return oddb.createOrder(od,odgs);
	}

	@Override
	public UserInfo getUserInfo(int uid, User loginuser) throws DaoException {
		if(loginuser.getDeptno()!=0)return null;
		return udb.queryUserInfoByUid(uid);
	}

	@Override
	public int createUser(UserInfo user) throws DaoException {
		return udb.createUser(user);
	}

	@Override
	public boolean updateAccount(int uid, UserInfo user) throws DaoException {
		return udb.updateAccount(uid, user);
	}

	@Override
	public boolean checkuser(String username) throws DaoException {
		return udb.checkUser(username);
	}

	@Override
	public boolean checkuser(String username, String phone) throws DaoException {
		return udb.checkUser(username,phone);
	}

	@Override
	public boolean changePwd(String username, String password) throws DaoException {
		return udb.changePwd(username, password);
	}

	@Override
	public boolean actUser(String username) throws DaoException {
		return udb.actUser(username);
	}

	@Override
	public List<Order> getOrderByStatus(int status) throws DaoException {
		return oddb.getOrderByStatus(status);
	}

	@Override
	public boolean changeOrderStatus(int oid, int status) throws DaoException {
		return oddb.changeOrderStatus(oid, status);
	}

	@Override
	public boolean updateOrder(int oid, Order nod) throws DaoException {
		return oddb.updateOrder(oid, nod);
	}

	@Override
	public List<Order> getOrderbyYear(String year) throws DaoException {
		return oddb.getOrderbyYear(year);
	}

	@Override
	public List<Order> getOrderbyMonth(String month) throws DaoException {
		return oddb.getOrderbyMonth(month);
	}

	@Override
	public List<Order> getOrderbyDay(String day) throws DaoException {
		return oddb.getOrderbyDay(day);
	}

	@Override
	public Date[] QueryDate(String type) throws DaoException {
		return ldb.QueryDate(type);
	}

	@Override
	public List<Finance> getFinancebyYear(String year) throws DaoException {
		return faedb.getFinancebyYear(year);
	}

	@Override
	public List<Finance> getFinancebyMonth(String month) throws DaoException {
		return faedb.getFinancebyMonth(month);
	}

	@Override
	public List<Finance> getFinancebyDay(String day) throws DaoException {
		return faedb.getFinancebyDay(day);
	}

	@Override
	public List<Finance> getFinancebyType(int type) throws DaoException {
		return faedb.getFinancebyType(type);
	}

	@Override
	public List<Finance> getFinancebyStatus(int status) throws DaoException {
		return faedb.getFinancebyStatus(status);
	}

	@Override
	public List<Order> getOrderByType(int type) throws DaoException {
		return oddb.getOrderByType(type);
	}

	@Override
	public Finance getFinancebyId(int id) throws DaoException {
		return faedb.getFinancebyId(id);
	}

	@Override
	public boolean addFinace(Finance fin) throws DaoException {
		return faedb.addFinace(fin);
	}

	@Override
	public boolean changeFinanceStatus(int oid, int status) throws DaoException {
		return faedb.changeFinanceStatus(oid, status);
	}

	@Override
	public boolean updateFinace(Finance fin) throws DaoException {
		return faedb.updateFinace(fin);
	}

}
