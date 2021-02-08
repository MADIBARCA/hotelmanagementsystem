package com.epam.hotelmanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.hotelmanagement.bean.Resident;
import com.epam.hotelmanagement.dao.ResidentDao;

/**
 * Servlet implementation class ResidentServlet
 */
@WebServlet("/")
public class ResidentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ResidentDao residentDao;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		residentDao = new ResidentDao();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		try {
			
			switch(action) {
			
			case "/new":   //if the action is new - call method
			   showNewForm(request, response);	
			   break;
			
			case "/insert":
			   insertResident(request, response);
			   break;
			
			case "/delete":
			   deleteResident(request, response);
			   break;
			
			case "/edit":
			   showEditForm(request, response);
			   break;
			
			case "/update":
			   updateResident(request, response);	
			   break;
			   
			default: 
			   listResident(request, response);
			   break;
			}
		
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
		
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("resident-form.jsp");
		dispatcher.forward(request, response);
	}
    
	//insert resident
	private void insertResident(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String name = request.getParameter("name"); //taking name from jsp page to create and insert new data
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		String room = request.getParameter("room");
		Resident newResident = new Resident(name, email, country, room);
		residentDao.insertResident(newResident);
		response.sendRedirect("list");
	}
	
	//delete resident
	private void deleteResident(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
		    residentDao.deleteResident(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("list");

	}
	
	//edit 
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Resident existingResident = residentDao.selectResident(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("resident-form.jsp");
		request.setAttribute("resident", existingResident);
		dispatcher.forward(request, response);

	}
	
	// update
	private void updateResident(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		String room = request.getParameter("room");

		Resident book = new Resident(id, name, email, country, room);
		residentDao.updateResident(book);
		response.sendRedirect("list");
	}
	
	//default
	// seeing the list of all users
	private void listResident(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Resident> listResident = residentDao.selectAllResidents();
		request.setAttribute("listResident", listResident);
		RequestDispatcher dispatcher = request.getRequestDispatcher("resident-list.jsp");
		dispatcher.forward(request, response);
	}

}
