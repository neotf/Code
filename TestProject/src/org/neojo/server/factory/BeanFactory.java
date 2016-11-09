package org.neojo.server.factory;

import org.neojo.server.action.Action;
import org.neojo.server.action.impl.ActionImpl;
import org.neojo.server.biz.Biz;
import org.neojo.server.biz.impl.BizImpl;
import org.neojo.server.dao.FinanceDao;
import org.neojo.server.dao.LoadDao;
import org.neojo.server.dao.OrderDao;
import org.neojo.server.dao.UserDao;
import org.neojo.server.dao.impl.FinanceDaoImpl;
import org.neojo.server.dao.impl.LoadDaoImpl;
import org.neojo.server.dao.impl.OrderDaoImpl;
import org.neojo.server.dao.impl.UserDaoImpl;

public abstract class BeanFactory {

	
	public static UserDao getUserInstance(String str) {
		if ("UserDaoImpl".equals(str)) {
			return new UserDaoImpl();
		}
		return null;
	}
	public static FinanceDao getFinanceInstance(String str) {
		if ("FinanceDaoImpl".equals(str)) {
			return new FinanceDaoImpl();
		}
		return null;
	}
	public static OrderDao getOrderInstance(String str) {
		if ("OrderDaoImpl".equals(str)) {
			return new OrderDaoImpl();
		}
		return null;
	}
	public static LoadDao getLoadInstance(String str){
		if ("LoadDaoImpl".equals(str)) {
			return new LoadDaoImpl();
		}
		return null;
	}
	

	// 创建BIZ对象
	public static Biz getBizInstance(String str) {
		if ("BizImpl".equals(str)) {
			return new BizImpl();
		}
		return null;
	}

	// 创建Action对象
	public static Action getActionInstance(String str) {
		if ("ActionImpl".equals(str)) {
			return new ActionImpl();
		}
		return null;
	}
}
