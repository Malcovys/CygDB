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
				throw new DaoException(e2.getMessage());
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
	public Table descibeTable(Table table, Database database) throws DaoException {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			
			String query = "describe " + database.getNom()+"."+table.getNom();
			resultat = statement.executeQuery(query);
			
			List<String> table_fileds = new ArrayList<String>();
			List<String> table_fields_type = new ArrayList<String>();
			
			while(resultat.next()) {
				table_fileds.add(resultat.getNString("Field"));
				table_fields_type.add(resultat.getNString("Type"));
			}
			
			table.setFields(table_fileds);
			table.setFields_type(table_fields_type);
			
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
			
		return table;	
	}

	@Override
	public void inseterInto(Database database, Table table, String[] values) throws DaoException {
		List<String> table_fields = table.getFields();
		List<String> table_fileds_type = table.getFields_type();
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		String fieldsString = new String();
		String valueSlots = new String();
		
		for(int i =0; i < table_fields.size(); i++) {
			fieldsString +=  table_fields.get(i) + (i == table_fields.size() - 1 ? "" : ",");
			valueSlots += (i == table_fields.size() - 1 ? "?" : "?,");
		}
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("insert into "+database.getNom()+"."+table.getNom()+"("+fieldsString+") values("+valueSlots+");");
			
			for(int i=0; i < table_fields.size(); i++) {
				switch(table_fileds_type.get(i)) 
				{
					case "int":
						preparedStatement.setInt(i+1, Integer.parseInt(values[i]));
						break;
					default:
						preparedStatement.setString(i+1, values[i]);
						break;
				}
				
			}
			
			preparedStatement.executeUpdate();
			connexion.commit();
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
		
	}

	@Override
	public List<List<String>> listTableLines(Database database, Table table) throws DaoException {
		List<List<String>> lines = new ArrayList<List<String>>();
		
		List<String> table_fields = table.getFields();
		List<String> table_fields_type = table.getFields_type();
		
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			
			String query = "select * from "+database.getNom()+"."+table.getNom();
			resultat = statement.executeQuery(query);
			
			while(resultat.next()) {
				List<String> line = new ArrayList<String>();
				
				for(int i=0; i < table_fields.size(); i++) {
					switch(table_fields_type.get(i)) 
					{
						case "int":
							line.add(Integer.toString(resultat.getInt(table_fields.get(i))));
							break;
						default:
							line.add(resultat.getNString(table_fields.get(i)));
							break;
					}
				}
				
				lines.add(line);
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
		
		return lines;
	}

}
