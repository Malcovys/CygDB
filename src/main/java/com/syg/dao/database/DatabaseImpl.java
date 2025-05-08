package com.syg.dao.database;

import java.sql.*;
import java.util.*;

import com.cyg.beans.Database;
import com.cyg.beans.Table;
import com.syg.dao.DaoException;
import com.syg.dao.DaoFactory;

public class DatabaseImpl implements DatabaseDao {
	private DaoFactory daoFactory;
	
	public DatabaseImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void create(Database database) throws DaoException {
		Connection connexion = null;
		Statement statement = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			
			String query = "CREATE DATABASE " +  database.getNom();
			statement.execute(query);
			
			connexion.commit();
		} catch(SQLException e) {
			try {
				if(connexion != null) {
					connexion.rollback();
				}
			} catch(SQLException e2) {
				throw new DaoException(e.getMessage());
			}
			throw new DaoException(e.getMessage());
		} finally {
			try {
				if(connexion != null) {
					connexion.close();
				}
			} catch(SQLException e) {
				throw new DaoException(e.getMessage());
			}
		}
	}

	@Override
	public List<Database> list() throws DaoException {
		List<Database> databases = new ArrayList<Database>();
		
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			
			String query = "SHOW DATABASES;";
			resultat = statement.executeQuery(query);
			
			while(resultat.next()) {
				Database database = new Database();
				database.setNom(resultat.getNString("Database"));
				database.getTables();
				
				databases.add(database);
			}
		} catch(SQLException e) {
			throw new DaoException(e.getMessage());
		}
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }
		
		return databases;
	}

	@Override
	public List<Table> listTable(Database database) throws DaoException {
		List<Table> tables = new ArrayList<Table>();
		
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			
			String query = "SHOW TABLES IN " + database.getNom();
			resultat = statement.executeQuery(query);
			
			int index = 0;
			while(resultat.next()) {
				Table _table = new Table();
				_table.setNom(resultat.getNString(index));
				
				tables.add(_table);
				
				index++;
			}
			
		} catch(SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
	        try {
	            if (connexion != null) {
	                connexion.close();  
	            }
	        } catch (SQLException e) {
	            throw new DaoException(e.getMessage());
	        }			
		}
		
		return tables;
	}

	@Override
	public void createTable(Table table) throws DaoException {
		// TODO Auto-generated method stub
		
	}

}
