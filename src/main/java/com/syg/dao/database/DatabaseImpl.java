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
			
			String query = "create database " +  database.getNom();
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
			
			String query = "show databases where `database` not in('information_schema', 'mysql', 'performance_schema','sys');";
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
			
			String query = "show TABLES in " + database.getNom();
			resultat = statement.executeQuery(query);
			
			while(resultat.next()) {
				Table _table = new Table();
				_table.setNom(resultat.getNString("Tables_in_"+database.getNom()));
				
				tables.add(_table);
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
	public void createTable(Table table, Database database) throws DaoException {
		Connection connexion = null;
		Statement statement = null;
		
		String colones = new String();
		String[] table_colones_name = table.getColones_name();
		String[] table_colones_type = table.getColones_type();
		
		for(int i = 0; i < table_colones_name.length; i++) {
			colones += table_colones_name[i] + " " + table_colones_type[i] + (i == table_colones_name.length - 1 ? "" : ",");
		}
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			
			String query = "create table " +  database.getNom() +"."+table.getNom() + " ( " + colones + " )engine=InnoDB default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci;";
			System.out.println(query);
			
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

}
