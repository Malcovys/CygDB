package com.syg.dao.database;

import java.util.List;

import com.cyg.beans.Database;
import com.cyg.beans.Table;
import com.syg.dao.DaoException;

public interface DatabaseDao {
	void create(Database database) throws DaoException;
	void createTable(Table table, Database database) throws DaoException;
	List<Database> list() throws DaoException;
	List<Table> listTable(Database database) throws DaoException;
	Table descibeTable(Table table, Database database) throws DaoException;
	void inseterInto(Database database, Table table, String[] values) throws DaoException;
}
