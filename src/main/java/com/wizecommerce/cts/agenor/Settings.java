package com.wizecommerce.cts.agenor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wizecommerce.cts.utils.Hibernate;

/**
 * Servlet implementation class Settings
 */
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Settings() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("submit") != null) {
			int rs = 0;
			Hibernate hibernate = new Hibernate();
			String query = "UPDATE UserSettings set recordsPerPage = '" + request.getParameter("recordsPerPage") + "' WHERE uId = '" + request.getParameter("userId") + "'";
			rs = hibernate.executeUpdateQuery(query);
			hibernate.terminateSession();
			
			response.sendRedirect("./General.jsp?rs="+rs);
		}
		else{
			response.sendRedirect("./");
		}
		
	}

}
