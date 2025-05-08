package com.cyg.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyg.beans.Database;
import com.cyg.beans.Table;
import com.syg.dao.DaoFactory;
import com.syg.dao.database.DatabaseDao;

/**
 * Servlet implementation class TableServelet
 */
@WebServlet("/TableServlet")
public class TableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseDao databaseDao;
       
    
    public void init() throws ServletException {
    	DaoFactory  daoFactory = DaoFactory.getInstance();
    	this.databaseDao  = daoFactory.getDatabaseDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Table table = new Table();
			table.setNom(request.getParameter("tb_name"));
		
			Database database = new Database();
			database.setNom(request.getParameter("db_name"));
			
			databaseDao.descibeTable(table, database);
			request.setAttribute("table", table);
			request.setAttribute("database_name", database.getNom());
			
		} catch(Exception e) {
			request.setAttribute("error", e.getMessage());
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/templates/new_tb_line_form.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Table table = new Table();
			table.setNom(request.getParameter("tb_name"));
		
			Database database = new Database();
			database.setNom(request.getParameter("db_name"));;
			
			String[] values = request.getParameterValues("value");
			
			databaseDao.descibeTable(table, database);
			
			databaseDao.inseterInto(database, table, values);
			
			response.sendRedirect("http://localhost:8080/cyg/database"); 
			
		} catch(Exception e) {
			request.setAttribute("error", e.getMessage());
		}
	}

}
