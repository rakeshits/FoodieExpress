<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/RestaurantServlet");
        return;
    }
    String error = (String) request.getAttribute("error");
    String next = request.getParameter("next");
    // Pre-compute the login URL to avoid quoting issues inside href attribute
    String loginUrl = request.getContextPath() + "/view/login.jsp"
            + (next != null && !next.isEmpty() ? "?next=" + java.net.URLEncoder.encode(next, "UTF-8") : "");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Register - FoodieExpress</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/register.css">
</head>
<body>

<a href="<%= request.getContextPath() %>/RestaurantServlet" class="logo">FOODIE<span>EXPRESS</span></a>
<p class="logo-subtitle">Create your account to start ordering</p>

<div class="card">
  <h2>JOIN FOODIEEXPRESS 🍕</h2>
  <p>Fill in the details below</p>

  <% if (error != null) { %>
    <div class="alert-error">⚠️ <%= error %></div>
  <% } %>

  <form method="post" action="<%= request.getContextPath() %>/RegisterServlet">
    <% if (next != null && !next.isEmpty()) { %>
      <input type="hidden" name="next" value="<%= next %>">
    <% } %>

    <div class="form-group">
      <label>Full Name</label>
      <input type="text" name="username" placeholder="John Doe" required>
    </div>
    <div class="form-group">
      <label>Email Address</label>
      <input type="email" name="email" placeholder="you@example.com" required>
    </div>
    <div class="form-group">
      <label>Password</label>
      <input type="password" name="password" placeholder="Min. 6 characters" required minlength="6">
      <div class="password-hint">At least 6 characters</div>
    </div>
    <div class="form-group">
      <label>Delivery Address</label>
      <textarea name="address" placeholder="Enter your full delivery address"></textarea>
    </div>
    <button type="submit" class="btn-submit">CREATE ACCOUNT →</button>
  </form>

  <div class="divider">or</div>
  <div class="link-login">
    Already have an account?
    <a href="<%= loginUrl %>">Login</a>
  </div>
</div>

<div class="back-link">
  <a href="<%= request.getContextPath() %>/RestaurantServlet">← Back to Restaurants</a>
</div>

</body>
</html>
