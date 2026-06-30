<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.model.Menu, com.model.Restaurant, com.model.User" %>
<%
    List<Menu> menus = (List<Menu>) request.getAttribute("menus");
    Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
    User loggedUser = (User) session.getAttribute("user");
    java.util.Map<Integer,Integer> cart = (java.util.Map<Integer,Integer>) session.getAttribute("cart");
    int cartCount = (cart != null) ? cart.values().stream().mapToInt(Integer::intValue).sum() : 0;
    Integer restaurantIdObj = (Integer) request.getAttribute("restaurantId");
    int restaurantId = (restaurantIdObj != null) ? restaurantIdObj : 0;
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><%= restaurant != null ? restaurant.getName() : "Menu" %> - FoodieExpress</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/menu.css">
</head>
<body>

<nav>
  <a href="<%= request.getContextPath() %>/RestaurantServlet" class="logo">FOODIE<span>EXPRESS</span></a>
  <div class="nav-right">
    <% if (loggedUser != null) { %>
      <span class="user-greeting">Hey, <strong><%= loggedUser.getUsername() %></strong></span>
      <a href="<%= request.getContextPath() %>/CartServlet" class="cart-btn">
        🛒 Cart
        <% if (cartCount > 0) { %><span class="cart-count"><%= cartCount %></span><% } %>
      </a>
      <a href="<%= request.getContextPath() %>/OrderHistoryServlet">My Orders</a>
      <a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a>
    <% } else { %>
      <a href="<%= request.getContextPath() %>/view/login.jsp" class="btn-nav-ghost">Login</a>
      <a href="<%= request.getContextPath() %>/view/register.jsp" class="btn-nav-cta">Sign Up</a>
    <% } %>
  </div>
</nav>

<% if (restaurant != null) { %>
<div class="resto-header">
  <div class="breadcrumb">
    <a href="<%= request.getContextPath() %>/RestaurantServlet">Home</a> / <%= restaurant.getName() %>
  </div>
  <div class="resto-info">
    <% if (restaurant.getImagePath() != null && !restaurant.getImagePath().isEmpty()) { %>
      <img src="<%= restaurant.getImagePath() %>" class="resto-img" alt="<%= restaurant.getName() %>"
           onerror="this.src='https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=200&q=80'">
    <% } else { %>
      <img src="https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=200&q=80" class="resto-img" alt="">
    <% } %>
    <div class="resto-details">
      <h1><%= restaurant.getName() %></h1>
      <div class="resto-meta">
        <span class="rating-chip">★ <%= String.format("%.1f", restaurant.getRating()) %></span>
        <span class="meta-chip">🍴 <%= restaurant.getCuisineType() %></span>
        <span class="meta-chip">⏱ <%= restaurant.getDeliveryTime() %> mins</span>
        <span class="meta-chip">📍 <%= restaurant.getAddress() %></span>
        <span class="<%= restaurant.isActive() ? "status-open" : "status-closed" %>">
          <%= restaurant.isActive() ? "● Open Now" : "● Closed" %>
        </span>
      </div>
    </div>
  </div>
</div>
<% } %>

<main>
  <% if (menus == null || menus.isEmpty()) { %>
    <div class="empty-menu">
      <div class="icon">🍽️</div>
      <h2>Menu not available yet</h2>
      <p style="color:#93959f; margin-top:8px;">Check back soon for delicious items!</p>
    </div>
  <% } else { %>
    <div class="section-title">
      📋 Menu Items
      <span class="menu-count">(<%= menus.size() %> items)</span>
    </div>

    <% for (Menu m : menus) {
       boolean inCart = cart != null && cart.containsKey(m.getMenuId());
    %>
    <div class="menu-item">
      <div class="item-details">
        <div class="veg-indicator veg"></div>
        <div class="item-name"><%= m.getItemName() %></div>
        <div class="item-price">₹<%= String.format("%.0f", m.getPrice()) %></div>
        <div class="item-desc"><%= m.getDescription() != null ? m.getDescription() : "" %></div>
        <% if (!m.isAvailable()) { %>
          <span class="unavailable-tag">Currently Unavailable</span>
        <% } %>
      </div>

      <div class="item-image-wrap">
        <% if (m.getImagePath() != null && !m.getImagePath().isEmpty()) { %>
          <img src="<%= m.getImagePath() %>" class="item-img" alt="<%= m.getItemName() %>"
               onerror="this.src='https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=200&q=80'">
        <% } else { %>
          <img src="https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=200&q=80" class="item-img" alt="">
        <% } %>

        <div class="add-btn-wrap">
          <% if (!m.isAvailable()) { %>
            <span class="btn-add-disabled">Unavailable</span>
          <% } else if (inCart) { %>
            <span class="in-cart-badge">✓ Added</span>
          <% } else { %>
            <form method="post" action="<%= request.getContextPath() %>/CartServlet" style="margin:0">
              <input type="hidden" name="action" value="add">
              <input type="hidden" name="menuId" value="<%= m.getMenuId() %>">
              <input type="hidden" name="restaurantId" value="<%= restaurantId %>">
              <button type="submit" class="btn-add">+ ADD</button>
            </form>
          <% } %>
        </div>
      </div>
    </div>
    <% } %>
  <% } %>
</main>

<% if (cartCount > 0) { %>
<div class="cart-bar">
  <div class="cart-bar-info">
    <strong><%= cartCount %> item<%= cartCount > 1 ? "s" : "" %></strong> in your cart
  </div>
  <a href="<%= request.getContextPath() %>/CartServlet" class="btn-checkout">View Cart →</a>
</div>
<% } %>

<footer>
  <p>© 2025 <span>FoodieExpress</span></p>
</footer>

</body>
</html>
