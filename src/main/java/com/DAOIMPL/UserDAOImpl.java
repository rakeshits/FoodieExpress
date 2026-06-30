package com.DAOIMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.DAO.UserDAO;
import com.model.User;
import com.util.DBConnection;

public class UserDAOImpl implements UserDAO {

	@Override
	public boolean addUser(User user) {
		String query = "INSERT INTO user (Username, Email, Password, Address, Role) VALUES (?, ?, ?, ?, ?)";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getAddress());
			pstmt.setString(5, user.getRole());
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User getUserById(int userId) {
		String query = "SELECT * FROM user WHERE UserID=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) return mapRow(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		String query = "SELECT * FROM user WHERE Email=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) return mapRow(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateUser(User user) {
		String query = "UPDATE user SET Username=?, Email=?, Password=?, Address=?, Role=? WHERE UserID=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getAddress());
			pstmt.setString(5, user.getRole());
			pstmt.setInt(6, user.getUserId());
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteUser(int userId) {
		String query = "DELETE FROM user WHERE UserID=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, userId);
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User validateUser(String email, String password) {
		String query = "SELECT * FROM user WHERE Email=? AND Password=?";
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) return mapRow(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private User mapRow(ResultSet rs) throws Exception {
		User user = new User();
		user.setUserId(rs.getInt("UserID"));
		user.setUsername(rs.getString("Username"));
		user.setEmail(rs.getString("Email"));
		user.setPassword(rs.getString("Password"));
		user.setAddress(rs.getString("Address"));
		user.setRole(rs.getString("Role"));
		user.setCreatedDate(rs.getTimestamp("CreatedDate"));
		user.setLastLoginDate(rs.getTimestamp("LastLoginDate"));
		return user;
	}
}
