package com.cyg.servlets;

import java.io.IOException;
import java.util.List;

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
			List<Database> databases;
 			databases = databaseDao.list();
 			
 			for( Database database : databases) {
 				List<Table> database_tables = databaseDao.listTable(database);
 				database.setTables(database_tables);
 			}
			
			request.setAttribute("databases", databases);
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
			Database database = new Database();
			database.setNom(request.getParameter("name"));
	
			databaseDao.create(database);
			
			List<Database> databases = databaseDao.list();
			request.setAttribute("databases", databases);
		} catch(Exception e) {
			request.setAttribute("error", e.getMessage());
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/templates/app.jsp").forward(request, response);
	}

}
