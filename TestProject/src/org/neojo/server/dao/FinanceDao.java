package org.neojo.server.dao;

import java.util.List;

import org.neojo.server.entity.Finance;
import org.neojo.server.exception.DaoException;

public interface FinanceDao {
	List<Finance> getAllFinance(int page) throws DaoException;
	Finance getFinancebyId(int id) throws DaoException;
	List<Finance> getFinancebyYear(String year) throws DaoException;
	List<Finance> getFinancebyMonth(String month) throws DaoException;
	List<Finance> getFinancebyDay(String day) throws DaoException;
	List<Finance> getFinancebyType(int type) throws DaoException;
	List<Finance> getFinancebyStatus(int status) throws DaoException;
	boolean addFinace(Finance fin) throws DaoException;
	boolean updateFinace(Finance fin) throws DaoException;
	boolean changeFinanceStatus(int oid,int status) throws DaoException;
}
