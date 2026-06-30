package com.model;

public class OrderItem {

	private int orderId;
	private int orderItemId;
	private int quantity;
	private double itemTotal;
	private int menuId;

	public OrderItem() {
		super();
	}

	public OrderItem(int orderId, int orderItemId,
			int quantity, double itemTotal, int menuId) {

		this.orderId = orderId;
		this.orderItemId = orderItemId;
		this.quantity = quantity;
		this.itemTotal = itemTotal;
		this.menuId = menuId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(double itemTotal) {
		this.itemTotal = itemTotal;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
}