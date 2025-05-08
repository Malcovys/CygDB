package com.cyg.servlets;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyg.beans.Database;
import com.cyg.beans.Table;
import com.syg.dao.DaoException;
import com.syg.dao.DaoFactory;
import com.syg.dao.database.DatabaseDao;

/**
 * Servlet implementation class Home
 */
@WebServlet("/DatabaseServlet")
public class DatabaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseDao databaseDao;
       
    
    public void init() throws ServletException {
    	DaoFactory  daoFactory = DaoFactory.getInstance();
    	this.databaseDao  = daoFactory.getDatabaseDao();
    }
    /**

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(request.getParameter("action") != null) {
				Table table = new Table();
				table.setNom(request.getParameter("tb_name"));
			
				Database database = new Database();
				database.setNom(request.getParameter("db_name"));
				
				databaseDao.descibeTable(table, database);
				
				table.setLines(databaseDao.listTableLines(database, table));
				
				request.setAttribute("table", table);
			}
			
			request.setAttribute("databases", getDatabases());
		} catch (DaoException e) {
			request.setAttribute("error", e.getMessage());
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/templates/app.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String request_action = request.getParameter("action");
			
			switch(request_action) 
			{
				case "new_db":
					createDatabase(request.getParameter("name"));
					break;
				case "new_tb":
					createTable(request.getParameter("db-nom"), request.getParameter("tb-nom"), request.getParameterValues("col-nom"), request.getParameterValues("col-type"));
					break;
				default:
					request.setAttribute("error", "Impossible de traité la requête");
					break;
			}	
			
			request.setAttribute("databases", getDatabases());
		} 
		catch (Exception e) {
			request.setAttribute("error", e.getMessage());
		}
	
		this.getServletContext().getRequestDispatcher("/WEB-INF/templates/app.jsp").forward(request, response);
	}
	
	private List<Database> getDatabases() throws DaoException {
		List<Database> databases = databaseDao.list();
		
		for(Database db : databases) {
				List<Table> database_tables = databaseDao.listTable(db);
				db.setTables(database_tables);
		}
		
		return databases;
	}
	
	private void createDatabase(String name) throws DaoException {
		Database new_database = new Database();
		new_database.setNom(name);
	
		databaseDao.create(new_database);
	}
	
	private void createTable(String database_name, String table_name, String[] table_colones, String[] colones_type) throws DaoException {
		Table new_table = new Table();
		new_table.setNom(table_name);
		new_table.setColones_name(table_colones);
		new_table.setColones_type(colones_type);
		
		Database database = new Database();
		database.setNom(database_name);
		
		databaseDao.createTable(new_table, database);
	}

}
