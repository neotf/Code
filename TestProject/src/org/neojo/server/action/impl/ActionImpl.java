package org.neojo.server.action.impl;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.neojo.server.action.Action;
import org.neojo.server.biz.Biz;
import org.neojo.server.entity.Finance;
import org.neojo.server.entity.OPType;
import org.neojo.server.entity.Order;
import org.neojo.server.entity.OrderGoods;
import org.neojo.server.entity.User;
import org.neojo.server.entity.UserInfo;
import org.neojo.server.exception.DaoException;
import org.neojo.server.factory.BeanFactory;

public class ActionImpl implements Action {
	private Biz biz =BeanFactory.getBizInstance("BizImpl");

	@Override
	public int loginAction(String username, String password) {
		try {
			return biz.login(username, password);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public User getUser(String username, String password) {
		try {
			return biz.getUser(username, password);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Finance> getAllFinance(int page) {
		try {
			return biz.getAllFinance(page);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Vector<User> getAllUserUid() {
		try {
			return biz.getAllUserUid();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Vector<OPType> queryFinType() {
		try {
			return biz.queryType("fintype");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Vector<OPType> queryPayType() {
		try {
			return biz.queryType("paytype");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Vector<OPType> queryOrderType() {
		try {
			return biz.queryType("ordertype");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<OrderGoods> getOrderGoodsbyOid(int oid) {
		try {
			return biz.getOrderGoodsbyOid(oid);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Order getOrderbyOid(int oid) {
		try {
			return biz.getOrderbyOid(oid);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

//	@Override
//	public List<Order> getAllOrder(int page) {
//		try {
//			return biz.getAllOrder(page);
//		} catch (DaoException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	@Override
	public Vector<OPType> queryOrderStatus() {
		try {
			return biz.queryType("orderstatus");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int createOrder(Order od, List<OrderGoods> odgs) {
		try {
			return biz.createOrder(od, odgs);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public UserInfo getUserInfo(int uid, User loginuser) {
		try {
			return biz.getUserInfo(uid, loginuser);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int createUser(UserInfo user) {
		try {
			return biz.createUser(user);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public boolean updateAccount(int uid, UserInfo user) {
		try {
			return biz.updateAccount(uid, user);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkuser(String username) {
		try {
			return biz.checkuser(username);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkuser(String username, String phone) {
		try {
			return biz.checkuser(username, phone);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean changePwd(String username, String password) {
		try {
			return biz.changePwd(username, password);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean actUser(String username) {
		try {
			return biz.actUser(username);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Order> getOrderByStatus(int status) {
		try {
			return biz.getOrderByStatus(status);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean changeOrderStatus(int oid, int status) {
		try {
			return biz.changeOrderStatus(oid, status);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateOrder(int oid, Order nod) {
		try {
			return biz.updateOrder(oid, nod);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Order> getOrderbyYear(String year) {
		try {
			return biz.getOrderbyYear(year);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Order> getOrderbyMonth(String month) {
		try {
			return biz.getOrderbyMonth(month);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Order> getOrderbyDay(String day) {
		try {
			return biz.getOrderbyDay(day);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Date[] QueryOrderDate() {
		try {
			return biz.QueryDate("orders");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Date[] QueryFinanceDate() {
		try {
			return biz.QueryDate("finance");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Finance> getFinancebyYear(String year) {
		try {
			return biz.getFinancebyYear(year);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Finance> getFinancebyMonth(String month) {
		try {
			return biz.getFinancebyMonth(month);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Finance> getFinancebyDay(String day) {
		try {
			return biz.getFinancebyDay(day);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Finance> getFinancebyType(int type) {
		try {
			return biz.getFinancebyType(type);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Finance> getFinancebyStatus(int status) {
		try {
			return biz.getFinancebyStatus(status);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Order> getOrderByType(int type) {
		try {
			return biz.getOrderByType(type);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Vector<OPType> queryFinStatus() {
		try {
			return biz.queryType("finstatus");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Finance getFinancebyId(int id) {
		try {
			return biz.getFinancebyId(id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addFinace(Finance fin) {
		try {
			return biz.addFinace(fin);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean changeFinanceStatus(int oid, int status) {
		try {
			return biz.changeFinanceStatus(oid, status);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateFinace(Finance fin) {
		try {
			return biz.updateFinace(fin);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
