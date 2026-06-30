<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/RestaurantServlet");
        return;
    }
    String error = (String) request.getAttribute("error");
    String registered = request.getParameter("registered");
    String next = request.getParameter("next");
    // Pre-compute the register URL to avoid quoting issues inside href attribute
    String registerUrl = request.getContextPath() + "/view/register.jsp"
            + (next != null && !next.isEmpty() ? "?next=" + java.net.URLEncoder.encode(next, "UTF-8") : "");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login - FoodieExpress</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css">
</head>
<body>

<a href="<%= request.getContextPath() %>/RestaurantServlet" class="logo">FOODIE<span>EXPRESS</span></a>
<p class="logo-subtitle">Login to place your order</p>

<div class="card">
  <h2>WELCOME BACK 👋</h2>
  <p>Enter your credentials below</p>

  <% if ("true".equals(registered)) { %>
    <div class="alert-success">✅ Registration successful! Please login.</div>
  <% } %>
  <% if (error != null) { %>
    <div class="alert-error">⚠️ <%= error %></div>
  <% } %>

  <form method="post" action="<%= request.getContextPath() %>/LoginServlet">
    <% if (next != null && !next.isEmpty()) { %>
      <input type="hidden" name="next" value="<%= next %>">
    <% } %>

    <div class="form-group">
      <label>Email Address</label>
      <input type="email" name="email" placeholder="you@example.com" required>
    </div>
    <div class="form-group">
      <label>Password</label>
      <input type="password" name="password" placeholder="Enter your password" required>
    </div>
    <button type="submit" class="btn-submit">LOGIN →</button>
  </form>

  <div class="divider">or</div>
  <div class="link-register">
    New here? <a href="<%= registerUrl %>">Create an account</a>
  </div>
</div>

<div class="back-link">
  <a href="<%= request.getContextPath() %>/RestaurantServlet">← Back to Restaurants</a>
</div>

</body>
</html>
