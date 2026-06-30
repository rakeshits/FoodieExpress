<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.Menu" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order Confirmed</title>
</head>

<body>

<h2>Order Placed Successfully</h2>

<%
Menu menu = (Menu) request.getAttribute("menu");
Integer quantity = (Integer) request.getAttribute("quantity");
Double total = (Double) request.getAttribute("total");
%>

<p>Item: <%=menu.getItemName()%></p>
<p>Quantity: <%=quantity%></p>
<p>Total: ₹ <%=total%></p>

<p><a href="<%=request.getContextPath()%>/RestaurantServlet">Back to Restaurants</a></p>

</body>
</html>