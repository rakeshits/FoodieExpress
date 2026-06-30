package com.servlets;

import java.io.IOException;
import java.util.List;

import com.DAOIMPL.OrderDAOImpl;
import com.DAOIMPL.RestaurantDAOImpl;
import com.model.OrderTable;
import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderHistoryServlet")
public class OrderHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet?next="
                    + java.net.URLEncoder.encode(request.getContextPath() + "/OrderHistoryServlet", "UTF-8"));
            return;
        }

        OrderDAOImpl orderDao = new OrderDAOImpl();
        List<OrderTable> orders = orderDao.getOrdersByUserId(user.getUserId());

        RestaurantDAOImpl restaurantDao = new RestaurantDAOImpl();
        request.setAttribute("orders", orders);
        request.setAttribute("restaurantDao", restaurantDao);
        request.getRequestDispatcher("view/orderHistory.jsp").forward(request, response);
    }
}
