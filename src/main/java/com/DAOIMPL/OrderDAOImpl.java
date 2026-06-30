package com.DAOIMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.DAO.OrderDAO;
import com.model.OrderTable;
import com.util.DBConnection;

public class OrderDAOImpl implements OrderDAO {

	@Override
	public boolean addOrder(OrderTable order) {
		String sql = "INSERT INTO ordertable(userid,orderdate,totalamount,status,paymentmethod,restaurantid) VALUES(?,?,?,?,?,?)";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, order.getUserId());
			pstmt.setTimestamp(2, new java.sql.Timestamp(order.getOrderDate().getTime()));
			pstmt.setDouble(3, order.getTotalAmount());
			pstmt.setString(4, order.getStatus());
			pstmt.setString(5, order.getPaymentMethod());
			pstmt.setInt(6, order.getRestaurantId());
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public OrderTable getOrderById(int orderId) {
		String sql = "SELECT * FROM ordertable WHERE orderid=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) return mapRow(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<OrderTable> getAllOrders() {
		List<OrderTable> orders = new ArrayList<>();
		String sql = "SELECT * FROM ordertable";
		try (Connection con = DBConnection.getConnection();
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) orders.add(mapRow(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	@Override
	public List<OrderTable> getOrdersByUserId(int userId) {
		List<OrderTable> orders = new ArrayList<>();
		String sql = "SELECT * FROM ordertable WHERE userid=? ORDER BY orderid ASC";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) orders.add(mapRow(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	@Override
	public boolean updateOrder(OrderTable order) {
		String sql = "UPDATE ordertable SET userid=?, orderdate=?, totalamount=?, status=?, paymentmethod=?, restaurantid=? WHERE orderid=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, order.getUserId());
			pstmt.setTimestamp(2, new java.sql.Timestamp(order.getOrderDate().getTime()));
			pstmt.setDouble(3, order.getTotalAmount());
			pstmt.setString(4, order.getStatus());
			pstmt.setString(5, order.getPaymentMethod());
			pstmt.setInt(6, order.getRestaurantId());
			pstmt.setInt(7, order.getOrderId());
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteOrder(int orderId) {
		String sql = "DELETE FROM ordertable WHERE orderid=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, orderId);
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private OrderTable mapRow(ResultSet rs) throws Exception {
		return new OrderTable(
			rs.getInt("orderid"),
			rs.getInt("userid"),
			rs.getTimestamp("orderdate"),
			rs.getDouble("totalamount"),
			rs.getString("status"),
			rs.getString("paymentmethod"),
			rs.getInt("restaurantid"));
	}
	
	public int addOrderAndGetId(OrderTable order) {

	    String sql =
	            "INSERT INTO ordertable(userid,orderdate,totalamount,status,paymentmethod,restaurantid) "
	            + "VALUES(?,?,?,?,?,?)";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

	        pstmt.setInt(1, order.getUserId());
	        pstmt.setTimestamp(2,
	                new java.sql.Timestamp(order.getOrderDate().getTime()));
	        pstmt.setDouble(3, order.getTotalAmount());
	        pstmt.setString(4, order.getStatus());
	        pstmt.setString(5, order.getPaymentMethod());
	        pstmt.setInt(6, order.getRestaurantId());

	        pstmt.executeUpdate();

	        ResultSet keys = pstmt.getGeneratedKeys();
	        if (keys.next()) {
	            return keys.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return -1;
	}
}
