<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.model.OrderTable, com.model.Restaurant, com.model.User, com.DAOIMPL.RestaurantDAOImpl" %>
<%
    User loggedUser = (User) session.getAttribute("user");
    if (loggedUser == null) {
        response.sendRedirect(request.getContextPath() + "/view/login.jsp");
        return;
    }
    List<OrderTable> orders = (List<OrderTable>) request.getAttribute("orders");
    RestaurantDAOImpl restaurantDao = (RestaurantDAOImpl) request.getAttribute("restaurantDao");
    java.util.Map<Integer,Integer> cart = (java.util.Map<Integer,Integer>) session.getAttribute("cart");
    int cartCount = (cart != null) ? cart.values().stream().mapToInt(Integer::intValue).sum() : 0;
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>My Orders - FoodieExpress</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/orderHistory.css">
</head>
<body>

<nav>
  <a href="<%= request.getContextPath() %>/RestaurantServlet" class="logo">FOODIE<span>EXPRESS</span></a>
  <div class="nav-right">
    <% if (cartCount > 0) { %>
      <a href="<%= request.getContextPath() %>/CartServlet" class="cart-btn">
        🛒 Cart
        <span class="cart-count"><%= cartCount %></span>
      </a>
    <% } %>
    <a href="<%= request.getContextPath() %>/RestaurantServlet">Restaurants</a>
    <a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a>
  </div>
</nav>

<main>
  <div class="page-header">
    <div class="page-title">MY ORDERS 📦</div>
    <a href="<%= request.getContextPath() %>/RestaurantServlet" class="btn-primary">+ New Order</a>
  </div>

  <% if (orders == null || orders.isEmpty()) { %>
    <div class="empty-state">
      <div class="icon">📦</div>
      <h2>No orders yet</h2>
      <p>You haven't placed any orders. Start ordering delicious food!</p>
      <a href="<%= request.getContextPath() %>/RestaurantServlet" class="btn-primary">Browse Restaurants</a>
    </div>
  <% } else { %>
    <%-- Show newest orders first --%>
    <% for (int i = orders.size() - 1; i >= 0; i--) {
        OrderTable o = orders.get(i);
        Restaurant r = (restaurantDao != null) ? restaurantDao.getRestaurantById(o.getRestaurantId()) : null;
        String statusClass = "status-" + (o.getStatus() != null ? o.getStatus() : "PLACED");
    %>
    <div class="order-card card">
      <div class="order-header">
        <div>
          <div class="order-id">Order <span class="id-hash">#</span><%= o.getOrderId() %></div>
          <div class="order-date">
            <%= o.getOrderDate() != null ? new java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a").format(o.getOrderDate()) : "—" %>
          </div>
        </div>
        <span class="status-badge <%= statusClass %>">
          ● <%= o.getStatus() != null ? o.getStatus() : "PLACED" %>
        </span>
      </div>

      <div class="order-body">
        <div class="restaurant-name">
          🏪 <%= r != null ? r.getName() : "Restaurant #" + o.getRestaurantId() %>
          <% if (r != null) { %>
            <span class="cuisine-sub"> | <%= r.getCuisineType() %></span>
          <% } %>
        </div>
        <div class="order-meta">
          <div class="meta-item">
            Total Amount
            <strong>₹<%= String.format("%.2f", o.getTotalAmount()) %></strong>
          </div>
          <div class="meta-item">
            Payment
            <strong><%= o.getPaymentMethod() != null ? o.getPaymentMethod() : "—" %></strong>
          </div>
          <% if (r != null) { %>
          <div class="meta-item">
            Restaurant Status
            <strong><%= r.isActive() ? "Open" : "Closed" %></strong>
          </div>
          <% } %>
        </div>
      </div>

      <div class="order-footer">
        <div class="total-amount">
          Total: <span class="amount-val">₹<%= String.format("%.2f", o.getTotalAmount()) %></span>
          <span class="tax-note">(incl. taxes)</span>
        </div>
        <% if (r != null && r.isActive()) { %>
          <a href="<%= request.getContextPath() %>/MenuServlet?restaurantId=<%= r.getRestaurantId() %>" class="btn-reorder">
            🔄 Reorder
          </a>
        <% } %>
      </div>
    </div>
    <% } %>
  <% } %>
</main>

<footer>
  <p>© 2025 <span class="brand">FOODIEEXPRESS</span></p>
</footer>

</body>
</html>
