package com.servlets;

import java.io.IOException;

import com.DAOIMPL.UserDAOImpl;
import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/view/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email    = request.getParameter("email");
		String password = request.getParameter("password");
		String next     = request.getParameter("next");

		UserDAOImpl dao = new UserDAOImpl();
		User user = dao.validateUser(email, password);

		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			if (next != null && !next.isEmpty()) {
				response.sendRedirect(next);
			} else {
				response.sendRedirect(request.getContextPath() + "/RestaurantServlet");
			}
		} else {
			request.setAttribute("error", "Invalid email or password. Please try again.");
			request.getRequestDispatcher("/view/login.jsp").forward(request, response);
		}
	}
}
