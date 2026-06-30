package com.servlets;

import java.io.IOException;
import java.util.List;

import com.DAOIMPL.MenuDAOImpl;
import com.DAOIMPL.RestaurantDAOImpl;
import com.model.Menu;
import com.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String param = request.getParameter("restaurantId");

        if (param == null || param.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/RestaurantServlet");
            return;
        }

        int restaurantId = Integer.parseInt(param);

        MenuDAOImpl menuDao = new MenuDAOImpl();
        RestaurantDAOImpl restaurantDao = new RestaurantDAOImpl();

        List<Menu> menus = menuDao.getMenusByRestaurantId(restaurantId);
        Restaurant restaurant = restaurantDao.getRestaurantById(restaurantId);

        request.setAttribute("menus", menus);
        request.setAttribute("restaurant", restaurant);
        request.setAttribute("restaurantId", restaurantId);

        request.getRequestDispatcher("view/menu.jsp").forward(request, response);
    }
}