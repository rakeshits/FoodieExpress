package com.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.DAOIMPL.MenuDAOImpl;
import com.DAOIMPL.OrderDAOImpl;
import com.DAOIMPL.OrderItemDAOImpl;
import com.DAOIMPL.RestaurantDAOImpl;
import com.model.Menu;
import com.model.OrderItem;
import com.model.OrderTable;
import com.model.Restaurant;
import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // Redirect via LoginServlet so next-URL flow is handled correctly
            response.sendRedirect(request.getContextPath() + "/LoginServlet?next="
                    + java.net.URLEncoder.encode(request.getContextPath() + "/CheckoutServlet", "UTF-8"));
            return;
        }

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/RestaurantServlet");
            return;
        }

        MenuDAOImpl menuDao = new MenuDAOImpl();
        List<Menu> menuItems = new ArrayList<>();
        double total = 0;

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Menu menu = menuDao.getMenuById(entry.getKey());
            if (menu != null) {
                menuItems.add(menu);
                total += menu.getPrice() * entry.getValue();
            }
        }

        // Fetch the restaurant from the session restaurantId (stored by CartServlet on add)
        Integer sessionRestaurantId = (Integer) session.getAttribute("restaurantId");
        Restaurant restaurant = null;
        if (sessionRestaurantId != null && sessionRestaurantId > 0) {
            RestaurantDAOImpl restaurantDao = new RestaurantDAOImpl();
            restaurant = restaurantDao.getRestaurantById(sessionRestaurantId);
        }

        request.setAttribute("menuItems", menuItems);
        request.setAttribute("cart", cart);
        request.setAttribute("total", total);
        request.setAttribute("restaurant", restaurant);
        request.getRequestDispatcher("/view/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/RestaurantServlet");
            return;
        }

        String paymentMethod = request.getParameter("paymentMethod");

        MenuDAOImpl menuDao = new MenuDAOImpl();
        double subtotal = 0;
        List<Menu> menuItems = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Menu menu = menuDao.getMenuById(entry.getKey());
            if (menu != null) {
                subtotal += menu.getPrice() * entry.getValue();
                menuItems.add(menu);
            }
        }

        double totalWithTax = subtotal + (subtotal * 0.05);

        // Get restaurantId from session (saved by CartServlet when item was added)
        Integer sessionRestaurantId = (Integer) session.getAttribute("restaurantId");
        int restaurantId = (sessionRestaurantId != null && sessionRestaurantId > 0) ? sessionRestaurantId : 0;

        OrderTable order = new OrderTable();
        order.setUserId(user.getUserId());
        order.setOrderDate(new Date());
        order.setTotalAmount(totalWithTax);
        order.setStatus("PLACED");
        order.setPaymentMethod(paymentMethod);
        order.setRestaurantId(restaurantId);

        OrderDAOImpl orderDao = new OrderDAOImpl();
        int generatedId = orderDao.addOrderAndGetId(order);

        if (generatedId == -1) {
            request.setAttribute("error", "Order placement failed. Please try again.");
            request.setAttribute("menuItems", menuItems);
            request.setAttribute("cart", cart);
            request.setAttribute("total", subtotal);
            request.getRequestDispatcher("/view/checkout.jsp").forward(request, response);
            return;
        }

        order.setOrderId(generatedId);

        OrderItemDAOImpl itemDao = new OrderItemDAOImpl();
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Menu menu = menuDao.getMenuById(entry.getKey());
            if (menu != null) {
                OrderItem item = new OrderItem();
                item.setOrderId(generatedId);
                item.setMenuId(entry.getKey());
                item.setQuantity(entry.getValue());
                item.setItemTotal(menu.getPrice() * entry.getValue());
                itemDao.addOrderItem(item);
            }
        }

        // Clear cart and restaurantId from session after successful order
        session.removeAttribute("cart");
        session.removeAttribute("restaurantId");

        request.setAttribute("order", order);
        request.setAttribute("menuItems", menuItems);
        request.setAttribute("totalAmount", totalWithTax);
        request.getRequestDispatcher("/view/orderSuccess.jsp").forward(request, response);
    }
}