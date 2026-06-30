package com.DAO;

import java.util.List;
import com.model.OrderTable;

public interface OrderDAO {

	boolean addOrder(OrderTable order);

	OrderTable getOrderById(int orderId);

	List<OrderTable> getAllOrders();

	List<OrderTable> getOrdersByUserId(int userId);

	boolean updateOrder(OrderTable order);

	boolean deleteOrder(int orderId);
}