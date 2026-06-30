package com.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // Redirect through LoginServlet so the next-URL flow works correctly
            response.sendRedirect(request.getContextPath() + "/LoginServlet?next="
                    + java.net.URLEncoder.encode(request.getContextPath() + "/CartServlet", "UTF-8"));
            return;
        }

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        request.setAttribute("cart", cart);
        request.getRequestDispatcher("view/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if ("add".equals(action)) {

            String restaurantIdParam = request.getParameter("restaurantId");

            if (user == null) {
                // Redirect to login, then back to menu page for this restaurant
                String next = request.getContextPath() + "/MenuServlet?restaurantId=" + restaurantIdParam;
                response.sendRedirect(request.getContextPath() + "/LoginServlet?next="
                        + java.net.URLEncoder.encode(next, "UTF-8"));
                return;
            }

            int menuId       = Integer.parseInt(request.getParameter("menuId"));
            int restaurantId = Integer.parseInt(restaurantIdParam);

            // Persist restaurantId in session so CheckoutServlet can use it
            session.setAttribute("restaurantId", restaurantId);

            @SuppressWarnings("unchecked")
            Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
            if (cart == null) {
                cart = new LinkedHashMap<>();
                session.setAttribute("cart", cart);
            }

            cart.merge(menuId, 1, Integer::sum);
            response.sendRedirect(request.getContextPath() + "/MenuServlet?restaurantId=" + restaurantId);

        } else if ("remove".equals(action)) {

            int menuId = Integer.parseInt(request.getParameter("menuId"));

            @SuppressWarnings("unchecked")
            Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
            if (cart != null) {
                cart.remove(menuId);
                if (cart.isEmpty()) {
                    session.removeAttribute("cart");
                }
            }
            response.sendRedirect(request.getContextPath() + "/CartServlet");

        } else if ("update".equals(action)) {

            int menuId = Integer.parseInt(request.getParameter("menuId"));
            int qty    = Integer.parseInt(request.getParameter("quantity"));

            @SuppressWarnings("unchecked")
            Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
            if (cart != null) {
                if (qty <= 0) {
                    cart.remove(menuId);
                    if (cart.isEmpty()) {
                        session.removeAttribute("cart");
                    }
                } else {
                    cart.put(menuId, qty);
                }
            }
            response.sendRedirect(request.getContextPath() + "/CartServlet");

        } else if ("clear".equals(action)) {

            session.removeAttribute("cart");
            response.sendRedirect(request.getContextPath() + "/RestaurantServlet");
        }
    }
}