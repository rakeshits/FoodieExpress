package com.servlets;

import java.io.IOException;
import java.util.List;

import com.DAOIMPL.RestaurantDAOImpl;
import com.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RestaurantServlet")
public class RestaurantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		RestaurantDAOImpl dao = new RestaurantDAOImpl();

		List<Restaurant> restaurants = dao.getAllRestaurants();

		request.setAttribute("restaurants", restaurants);

		request.getRequestDispatcher("view/resto.jsp")
				.forward(request, response);
	}
}