package org.neojo.server.dao;

import java.util.List;

import org.neojo.server.entity.Order;
import org.neojo.server.entity.OrderGoods;
import org.neojo.server.exception.DaoException;

public interface OrderDao {
	List<Order> getAllOrder(int page) throws DaoException;
	List<Order> getOrderbyYear(String year) throws DaoException;
	List<Order> getOrderbyMonth(String month) throws DaoException;
	List<Order> getOrderbyDay(String day) throws DaoException;
	List<Order> getOrderByStatus(int status) throws DaoException;
	List<Order> getOrderByType(int type) throws DaoException;
	
	boolean delOrderById(int id);

	Order getOrderbyOid(int oid) throws DaoException;

	List<OrderGoods> getOrderGoodsbyOid(int oid) throws DaoException;

	int createOrder(Order od,List<OrderGoods> odgs) throws DaoException;

	boolean updateOrder(int oid, Order nod) throws DaoException;
	boolean changeOrderStatus(int oid,int status) throws DaoException;
}
