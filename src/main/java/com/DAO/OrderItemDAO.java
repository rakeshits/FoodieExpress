package com.DAO;

import java.util.List;
import com.model.OrderItem;

public interface OrderItemDAO {

	boolean addOrderItem(OrderItem orderItem);

	OrderItem getOrderItemById(int orderItemId);

	List<OrderItem> getAllOrderItems();

	List<OrderItem> getOrderItemsByOrderId(int orderId);

	boolean updateOrderItem(OrderItem orderItem);

	boolean deleteOrderItem(int orderItemId);
}