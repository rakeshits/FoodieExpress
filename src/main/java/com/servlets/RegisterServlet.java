package com.servlets;

import java.io.IOException;

import com.DAOIMPL.UserDAOImpl;
import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/view/register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String email    = request.getParameter("email");
		String password = request.getParameter("password");
		String address  = request.getParameter("address");
		String next     = request.getParameter("next");

		UserDAOImpl dao = new UserDAOImpl();

		User existing = dao.getUserByEmail(email);
		if (existing != null) {
			request.setAttribute("error", "Email already registered. Please login.");
			request.getRequestDispatcher("/view/register.jsp").forward(request, response);
			return;
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setAddress(address);
		user.setRole("CUSTOMER");

		boolean success = dao.addUser(user);

		if (success) {
			String redirect = request.getContextPath() + "/view/login.jsp?registered=true";
			if (next != null && !next.isEmpty()) {
				redirect += "&next=" + java.net.URLEncoder.encode(next, "UTF-8");
			}
			response.sendRedirect(redirect);
		} else {
			request.setAttribute("error", "Registration failed. Please try again.");
			request.getRequestDispatcher("/view/register.jsp").forward(request, response);
		}
	}
}
