<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map, java.util.LinkedHashMap, com.model.Menu, com.model.User, com.DAOIMPL.MenuDAOImpl" %>
<%
    User loggedUser = (User) session.getAttribute("user");
    if (loggedUser == null) {
        response.sendRedirect(request.getContextPath() + "/LoginServlet?next="
                + java.net.URLEncoder.encode(request.getContextPath() + "/CartServlet", "UTF-8"));
        return;
    }
    Map<Integer,Integer> cart = (Map<Integer,Integer>) session.getAttribute("cart");
    MenuDAOImpl menuDao = new MenuDAOImpl();
    Map<Integer, Menu> menuCache = new LinkedHashMap<>();
    double grandTotal = 0;
    int totalItems = 0;
    if (cart != null) {
        for (Map.Entry<Integer,Integer> entry : cart.entrySet()) {
            Menu m = menuDao.getMenuById(entry.getKey());
            if (m != null) {
                menuCache.put(entry.getKey(), m);
                grandTotal += m.getPrice() * entry.getValue();
                totalItems += entry.getValue();
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Your Cart - FoodieExpress</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cart.css">

</head>
<body>

<nav>
  <a href="<%= request.getContextPath() %>/RestaurantServlet" class="logo">FOODIE<span>EXPRESS</span></a>
  <div class="nav-right">
    <a href="<%= request.getContextPath() %>/OrderHistoryServlet">My Orders</a>
    <a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a>
  </div>
</nav>

<main>
  <% if (cart == null || cart.isEmpty()) { %>
    <div class="empty-cart">
      <div class="icon">🛒</div>
      <h2>Your cart is empty</h2>
      <p>Looks like you haven't added anything yet.</p>
      <a href="<%= request.getContextPath() %>/RestaurantServlet" class="btn-browse">Browse Restaurants</a>
    </div>
  <% } else { %>
    <div class="page-title">Your Cart 🛒</div>
    <div class="page-sub"><%= totalItems %> item<%= totalItems > 1 ? "s" : "" %> selected</div>

    <div class="cart-layout">
      <div class="cart-items">
        <% for (Map.Entry<Integer,Integer> entry : cart.entrySet()) {
            Menu m = menuCache.get(entry.getKey());
            if (m == null) continue;
            int qty = entry.getValue();
            double subtotal = m.getPrice() * qty;
        %>
        <div class="cart-item">
          <% if (m.getImagePath() != null && !m.getImagePath().isEmpty()) { %>
            <img src="<%= m.getImagePath() %>" class="cart-item-img" alt="<%= m.getItemName() %>"
                 onerror="this.src='https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=150&q=80'">
          <% } else { %>
            <img src="https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=150&q=80" class="cart-item-img" alt="">
          <% } %>

          <div class="cart-item-details">
            <div class="cart-item-name"><%= m.getItemName() %></div>
            <div class="cart-item-price">₹<%= String.format("%.0f", m.getPrice()) %></div>
            <div class="cart-item-subtotal">Subtotal: ₹<%= String.format("%.0f", subtotal) %></div>
            <div class="qty-controls">
              <form method="post" action="<%= request.getContextPath() %>/CartServlet" style="display:inline">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="menuId" value="<%= m.getMenuId() %>">
                <input type="hidden" name="quantity" value="<%= qty - 1 %>">
                <button type="submit" class="qty-btn">−</button>
              </form>
              <span class="qty-val"><%= qty %></span>
              <form method="post" action="<%= request.getContextPath() %>/CartServlet" style="display:inline">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="menuId" value="<%= m.getMenuId() %>">
                <input type="hidden" name="quantity" value="<%= qty + 1 %>">
                <button type="submit" class="qty-btn">+</button>
              </form>
            </div>
          </div>

          <form method="post" action="<%= request.getContextPath() %>/CartServlet">
            <input type="hidden" name="action" value="remove">
            <input type="hidden" name="menuId" value="<%= m.getMenuId() %>">
            <button type="submit" class="btn-remove">✕ Remove</button>
          </form>
        </div>
        <% } %>
      </div>

      <div class="cart-summary">
        <div class="summary-card">
          <h3>Bill Details</h3>
          <div class="summary-row">
            <span>Item Total</span>
            <span>₹<%= String.format("%.2f", grandTotal) %></span>
          </div>
          <div class="summary-row">
            <span>Delivery Fee</span>
            <span class="delivery-free">FREE</span>
          </div>
          <div class="summary-row">
            <span>Taxes & Charges</span>
            <span>₹<%= String.format("%.2f", grandTotal * 0.05) %></span>
          </div>
          <div class="summary-row total">
            <span>To Pay</span>
            <span>₹<%= String.format("%.2f", grandTotal * 1.05) %></span>
          </div>
          <a href="<%= request.getContextPath() %>/CheckoutServlet" class="btn-checkout">
            Proceed to Checkout →
          </a>
          <form method="post" action="<%= request.getContextPath() %>/CartServlet">
            <input type="hidden" name="action" value="clear">
           <button type="submit" class="btn-clear" onclick="return confirm('Are you sure you want to clear your cart?')">Clear cart</button>
          </form>
        </div>
      </div>
    </div>
  <% } %>
</main>

<footer>
  <p>© 2025 <span class="brand">FOODIEEXPRESS</span></p>
</footer>

</body>
</html>
