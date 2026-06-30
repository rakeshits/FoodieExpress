<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.model.Restaurant, com.model.User" %>
<%
    List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
    User loggedUser = (User) session.getAttribute("user");
    java.util.Map<Integer,Integer> cart = (java.util.Map<Integer,Integer>) session.getAttribute("cart");
    int cartCount = (cart != null) ? cart.values().stream().mapToInt(Integer::intValue).sum() : 0;
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>FoodieExpress - Order Food Online</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/resto.css">
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

<div class="hero">
  <span class="hero-eyebrow">🔥 Delivered Fresh, Fast &amp; Hot</span>
  <h1>HUNGRY?<br>WE'VE GOT YOU</h1>
  <div class="search-bar">
    <input type="text" id="searchInput" placeholder="Search restaurants or cuisines..." onkeyup="filterCards()">
    <button>🔍 Search</button>
  </div>
</div>

<main>
  <div class="filters">
    <span class="chip active" onclick="filterByType('all', this)">All</span>
    <span class="chip" onclick="filterByType('open', this)">Open Now</span>
    <span class="chip" onclick="filterByType('fast', this)">Fast Delivery</span>
    <span class="chip" onclick="filterByType('rated', this)">Top Rated</span>
  </div>

  <div class="section-title">
    RESTAURANTS NEAR YOU
    <span class="count-badge"><%= restaurants != null ? restaurants.size() : 0 %></span>
  </div>

  <% if (restaurants == null || restaurants.isEmpty()) { %>
    <div class="empty-state">
      <div class="icon">🍽️</div>
      <h2>No Restaurants Found</h2>
      <p>We are onboarding restaurants in your area. Check back soon!</p>
    </div>
  <% } else { %>
  <div class="restaurants-grid" id="restaurantsGrid">
    <% for (Restaurant r : restaurants) { %>
    <div class="restaurant-card"
         data-name="<%= r.getName().toLowerCase() %>"
         data-cuisine="<%= r.getCuisineType() != null ? r.getCuisineType().toLowerCase() : "" %>"
         data-open="<%= r.isActive() %>"
         data-time="<%= r.getDeliveryTime() %>"
         data-rating="<%= r.getRating() %>">

      <div class="card-image">
        <% if (r.getImagePath() != null && !r.getImagePath().isEmpty()) { %>
          <img src="<%= r.getImagePath() %>" alt="<%= r.getName() %>" onerror="this.src='https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400&q=80'">
        <% } else { %>
          <img src="https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400&q=80" alt="<%= r.getName() %>">
        <% } %>
        <span class="status-badge <%= r.isActive() ? "status-open" : "status-closed" %>">
          <%= r.isActive() ? "● OPEN" : "● CLOSED" %>
        </span>
        <div class="offer-badge">🕐 <%= r.getDeliveryTime() %> mins delivery</div>
      </div>

      <div class="card-body">
        <h3 title="<%= r.getName() %>"><%= r.getName() %></h3>
        <div class="cuisine-tag">🍴 <%= r.getCuisineType() %></div>
        <div class="card-meta">
          <span class="rating <%= r.getRating() < 3.5 ? "low" : "" %>">
            ★ <%= String.format("%.1f", r.getRating()) %>
          </span>
          <span class="delivery-time">⏱ <%= r.getDeliveryTime() %> min</span>
         <%
  String addr = r.getAddress() != null ? r.getAddress() : "";
%>
<span class="address-info" title="<%= addr %>">
  📍 <%= addr.length() > 20 ? addr.substring(0, 20) + "…" : addr %>
</span>
        </div>
      </div>

      <div class="card-footer">
        <% if (r.isActive()) { %>
          <a href="<%= request.getContextPath() %>/MenuServlet?restaurantId=<%= r.getRestaurantId() %>" class="btn-menu">
            View Menu →
          </a>
        <% } else { %>
          <span class="btn-menu-closed">Currently Closed</span>
        <% } %>
      </div>
    </div>
    <% } %>
  </div>
  <% } %>
</main>

<footer>
  <p>© 2026 <span class="brand">FOODIEEXPRESS</span> — Made with ❤️ for food lovers</p>
</footer>

<script>
  function filterCards() {
    const query = document.getElementById('searchInput').value.toLowerCase();
    document.querySelectorAll('.restaurant-card').forEach(card => {
      const name = card.dataset.name;
      const cuisine = card.dataset.cuisine;
      card.style.display = (name.includes(query) || cuisine.includes(query)) ? '' : 'none';
    });
  }

  function filterByType(type, el) {
    document.querySelectorAll('.chip').forEach(c => c.classList.remove('active'));
    el.classList.add('active');
    document.querySelectorAll('.restaurant-card').forEach(card => {
      if (type === 'all') { card.style.display = ''; return; }
      if (type === 'open')  { card.style.display = card.dataset.open === 'true' ? '' : 'none'; return; }
      if (type === 'fast')  { card.style.display = parseInt(card.dataset.time) <= 30 ? '' : 'none'; return; }
      if (type === 'rated') { card.style.display = parseFloat(card.dataset.rating) >= 4.0 ? '' : 'none'; return; }
    });
  }
</script>
</body>
</html>
