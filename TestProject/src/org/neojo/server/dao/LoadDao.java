package org.neojo.server.dao;

import java.util.Date;
import java.util.Vector;

import org.neojo.server.entity.OPType;
import org.neojo.server.exception.DaoException;

public interface LoadDao {
	Vector<OPType> QueryType(String type) throws DaoException;
	Date[] QueryDate (String type) throws DaoException;

}
