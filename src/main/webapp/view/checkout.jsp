<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, com.model.Menu, com.model.Restaurant, com.model.User" %>
<%
    User loggedUser = (User) session.getAttribute("user");
    if (loggedUser == null) {
        response.sendRedirect(request.getContextPath() + "/view/login.jsp");
        return;
    }
    List<Menu> menuItems = (List<Menu>) request.getAttribute("menuItems");
    Map<Integer,Integer> cart = (Map<Integer,Integer>) request.getAttribute("cart");
    Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
    Double total = (Double) request.getAttribute("total");
    if (total == null) total = 0.0;
    String error = (String) request.getAttribute("error");
    double tax = total * 0.05;
    double toPay = total + tax;
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Checkout - FoodieExpress</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/checkout.css">

</head>
<body>

<nav>
  <a href="<%= request.getContextPath() %>/RestaurantServlet" class="logo">FOODIE<span>EXPRESS</span></a>
  <div class="nav-right">
    <a href="<%= request.getContextPath() %>/CartServlet">← Back to Cart</a>
  </div>
</nav>

<main>
  <div class="breadcrumb">
    <a href="<%= request.getContextPath() %>/RestaurantServlet">Home</a> /
    <a href="<%= request.getContextPath() %>/CartServlet">Cart</a> / Checkout
  </div>
  <div class="page-title">Checkout 🧾</div>

  <% if (error != null) { %>
    <div class="alert-error">⚠️ <%= error %></div>
  <% } %>

  <form method="post" action="<%= request.getContextPath() %>/CheckoutServlet">
  <div class="checkout-layout">

    <div class="checkout-left">

      <!-- DELIVERY ADDRESS -->
      <div class="section-card">
        <h3>📍 Delivery Address</h3>
        <div class="address-box">
          <div class="label">Delivering to</div>
          <strong><%= loggedUser.getUsername() %></strong><br>
          <%= loggedUser.getAddress() != null && !loggedUser.getAddress().isEmpty()
              ? loggedUser.getAddress() : "No address saved — please update your profile." %>
        </div>
      </div>

      <!-- ORDER ITEMS -->
      <div class="section-card">
        <h3>🛍️ Order Summary
          <% if (restaurant != null) { %>
            <span class="order-restaurant-note">from <%= restaurant.getName() %></span>
          <% } %>
        </h3>
        <% if (menuItems != null) {
            for (Menu m : menuItems) {
                int qty = cart != null && cart.containsKey(m.getMenuId()) ? cart.get(m.getMenuId()) : 1;
        %>
        <div class="order-item">
          <% if (m.getImagePath() != null && !m.getImagePath().isEmpty()) { %>
            <img src="<%= m.getImagePath() %>" class="order-item-img" alt=""
                 onerror="this.src='https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=100&q=80'">
          <% } else { %>
            <img src="https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=100&q=80" class="order-item-img" alt="">
          <% } %>
          <div class="order-item-info">
            <div class="order-item-name"><%= m.getItemName() %></div>
            <div class="order-item-qty">Qty: <%= qty %> × ₹<%= String.format("%.0f", m.getPrice()) %></div>
          </div>
          <div class="order-item-price">₹<%= String.format("%.0f", m.getPrice() * qty) %></div>
        </div>
        <% } } %>
      </div>

      <!-- PAYMENT -->
      <div class="section-card">
        <h3>💳 Payment Method</h3>
        <div class="payment-options">
          <label class="payment-option">
            <input type="radio" name="paymentMethod" value="COD" checked>
            <span class="pay-icon">💵</span>
            <div>
              <div class="pay-label">Cash on Delivery</div>
              <div class="pay-desc">Pay when your order arrives</div>
            </div>
          </label>
          <label class="payment-option">
            <input type="radio" name="paymentMethod" value="UPI">
            <span class="pay-icon">📱</span>
            <div>
              <div class="pay-label">UPI</div>
              <div class="pay-desc">Pay via Google Pay, PhonePe, Paytm</div>
            </div>
          </label>
          <label class="payment-option">
            <input type="radio" name="paymentMethod" value="CARD">
            <span class="pay-icon">💳</span>
            <div>
              <div class="pay-label">Credit / Debit Card</div>
              <div class="pay-desc">Visa, Mastercard, RuPay</div>
            </div>
          </label>
        </div>
      </div>
    </div>

    <!-- BILL SUMMARY -->
    <div class="checkout-right">
      <div class="section-card">
        <h3>🧾 Bill Details</h3>
        <div class="bill-row">
          <span>Item Total</span>
          <span>₹<%= String.format("%.2f", total) %></span>
        </div>
        <div class="bill-row">
          <span>Delivery Fee</span>
          <span class="free">FREE</span>
        </div>
        <div class="bill-row">
          <span>GST (5%)</span>
          <span>₹<%= String.format("%.2f", tax) %></span>
        </div>
        <div class="bill-row total">
          <span>Total Payable</span>
          <span>₹<%= String.format("%.2f", toPay) %></span>
        </div>
        <button type="submit" class="btn-place-order">🎉 Place Order</button>
        <div class="secure-note">🔒 Safe & Secure Payments</div>
      </div>
    </div>

  </div>
  </form>
</main>

<footer>
  <p>© 2025 <span class="brand">FOODIEEXPRESS</span></p>
</footer>

<script>
  document.querySelectorAll('.payment-option').forEach(opt => {
    opt.addEventListener('click', () => {
      document.querySelectorAll('.payment-option').forEach(o => o.classList.remove('selected'));
      opt.classList.add('selected');
    });
  });
  document.querySelector('.payment-option').classList.add('selected');
</script>
</body>
</html>
