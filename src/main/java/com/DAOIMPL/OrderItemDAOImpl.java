package com.DAOIMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.DAO.OrderItemDAO;
import com.model.OrderItem;
import com.util.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

	@Override
	public boolean addOrderItem(OrderItem orderItem) {
		String sql = "INSERT INTO orderitem(orderid,quantity,itemtotal,menuid) VALUES(?,?,?,?)";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, orderItem.getOrderId());
			pstmt.setInt(2, orderItem.getQuantity());
			pstmt.setDouble(3, orderItem.getItemTotal());
			pstmt.setInt(4, orderItem.getMenuId());
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public OrderItem getOrderItemById(int orderItemId) {
		String sql = "SELECT * FROM orderitem WHERE orderitemid=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, orderItemId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) return mapRow(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<OrderItem> getAllOrderItems() {
		List<OrderItem> items = new ArrayList<>();
		String sql = "SELECT * FROM orderitem";
		try (Connection con = DBConnection.getConnection();
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) items.add(mapRow(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public List<OrderItem> getOrderItemsByOrderId(int orderId) {
		List<OrderItem> items = new ArrayList<>();
		String sql = "SELECT * FROM orderitem WHERE orderid=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) items.add(mapRow(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public boolean updateOrderItem(OrderItem orderItem) {
		String sql = "UPDATE orderitem SET orderid=?, quantity=?, itemtotal=?, menuid=? WHERE orderitemid=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, orderItem.getOrderId());
			pstmt.setInt(2, orderItem.getQuantity());
			pstmt.setDouble(3, orderItem.getItemTotal());
			pstmt.setInt(4, orderItem.getMenuId());
			pstmt.setInt(5, orderItem.getOrderItemId());
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteOrderItem(int orderItemId) {
		String sql = "DELETE FROM orderitem WHERE orderitemid=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, orderItemId);
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private OrderItem mapRow(ResultSet rs) throws Exception {
		return new OrderItem(
			rs.getInt("orderid"),
			rs.getInt("orderitemid"),
			rs.getInt("quantity"),
			rs.getDouble("itemtotal"),
			rs.getInt("menuid"));
	}
}
