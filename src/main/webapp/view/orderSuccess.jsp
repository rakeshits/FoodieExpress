<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.model.Menu, com.model.OrderTable, com.model.User" %>
<%
    User loggedUser = (User) session.getAttribute("user");
    if (loggedUser == null) {
        response.sendRedirect(request.getContextPath() + "/RestaurantServlet");
        return;
    }
    OrderTable order = (OrderTable) request.getAttribute("order");
    List<Menu> menuItems = (List<Menu>) request.getAttribute("menuItems");
    Double totalAmount = (Double) request.getAttribute("totalAmount");
    if (totalAmount == null) totalAmount = 0.0;
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Order Placed! - FoodieExpress</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/orderSuccess.css">
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
  <div class="success-circle">
    <svg viewBox="0 0 24 24" fill="none" stroke-width="3" stroke-linecap="round" stroke-linejoin="round">
      <polyline points="20 6 9 17 4 12"/>
    </svg>
  </div>

  <h1>ORDER PLACED! 🎉</h1>
  <p class="subtitle">Thank you, <strong><%= loggedUser.getUsername() %></strong>! Your food is being prepared.</p>

  <div class="tracker">
    <h3>📦 Order Status</h3>
    <div class="steps">
      <div class="step">
        <div class="step-dot done">✓</div>
        <div class="step-label done">Placed</div>
      </div>
      <div class="step">
        <div class="step-dot active">🍳</div>
        <div class="step-label active">Preparing</div>
      </div>
      <div class="step">
        <div class="step-dot pending">🛵</div>
        <div class="step-label pending">On the way</div>
      </div>
      <div class="step">
        <div class="step-dot pending">🏠</div>
        <div class="step-label pending">Delivered</div>
      </div>
    </div>
  </div>

  <% if (order != null) { %>
  <div class="order-card">
    <h3>🧾 Order Details</h3>
    <div class="detail-row">
      <span class="detail-label">Order ID</span>
      <span class="detail-value">#<%= order.getOrderId() %></span>
    </div>
    <div class="detail-row">
      <span class="detail-label">Status</span>
      <span class="detail-value"><span class="status-badge">● <%= order.getStatus() %></span></span>
    </div>
    <div class="detail-row">
      <span class="detail-label">Payment</span>
      <span class="detail-value"><%= order.getPaymentMethod() %></span>
    </div>
    <div class="detail-row">
      <span class="detail-label">Deliver to</span>
      <span class="detail-value"><%= loggedUser.getAddress() != null && !loggedUser.getAddress().isEmpty() ? loggedUser.getAddress() : "—" %></span>
    </div>
    <div class="detail-row">
      <span class="detail-label">Total Paid</span>
      <span class="detail-value detail-value-red">₹<%= String.format("%.2f", totalAmount) %></span>
    </div>

    <% if (menuItems != null && !menuItems.isEmpty()) { %>
    <div class="items-wrap">
      <% for (Menu m : menuItems) { %>
      <div class="item-row">
        <% if (m.getImagePath() != null && !m.getImagePath().isEmpty()) { %>
          <img src="<%= m.getImagePath() %>" class="item-img" alt=""
               onerror="this.src='https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=100&q=80'">
        <% } else { %>
          <img src="https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=100&q=80" class="item-img" alt="">
        <% } %>
        <span class="item-name"><%= m.getItemName() %></span>
        <span class="item-price">₹<%= String.format("%.0f", m.getPrice()) %></span>
      </div>
      <% } %>
    </div>
    <% } %>
  </div>
  <% } %>

  <div class="actions">
    <a href="<%= request.getContextPath() %>/OrderHistoryServlet" class="btn-secondary">View All Orders</a>
    <a href="<%= request.getContextPath() %>/RestaurantServlet" class="btn-primary">Order Again 🍔</a>
  </div>
</main>

<footer>
  <p>© 2025 <span class="brand">FOODIEEXPRESS</span></p>
</footer>

</body>
</html>