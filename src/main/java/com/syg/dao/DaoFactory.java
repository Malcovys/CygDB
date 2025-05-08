package com.syg.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.syg.dao.database.*;
import com.syg.dao.database.DatabaseImpl;

public class DaoFactory {
	/* 
	 * Cette class est responsable de la connection avec la base de donnée
	 */
	private String url;
	private String username;
	private String password;
	
	DaoFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public static DaoFactory getInstance() {
		try {
			// com.mysql.cj.jdbc.Driver ?
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		DaoFactory instance = new DaoFactory("jdbc:mysql://localhost:3306/", "root", "Malcovys12;");
		
		return instance;
	}
	
	public Connection getConnection() throws SQLException {
		Connection connexion = DriverManager.getConnection(url, username, password);
		connexion.setAutoCommit(false);
		return connexion;
	}
	
	
	public DatabaseDao getDatabaseDao() {
		return new DatabaseImpl(this);
	}
}