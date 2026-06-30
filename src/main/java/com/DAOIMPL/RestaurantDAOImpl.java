package com.DAOIMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.DAO.RestaurantDAO;
import com.model.Restaurant;
import com.util.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {

	@Override
	public boolean addRestaurant(Restaurant restaurant) {

		String sql =
				"INSERT INTO restaurant(name,cuisinetype,deliverytime,address,rating,isactive,imagepath,description,priceRange,openingTime,closingTime) "
						+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, restaurant.getName());
			pstmt.setString(2, restaurant.getCuisineType());
			pstmt.setInt(3, restaurant.getDeliveryTime());
			pstmt.setString(4, restaurant.getAddress());
			pstmt.setDouble(5, restaurant.getRating());
			pstmt.setBoolean(6, restaurant.isActive());
			pstmt.setString(7, restaurant.getImagePath());

			pstmt.setString(8, restaurant.getDescription());
			pstmt.setString(9, restaurant.getPriceRange());
			pstmt.setString(10, restaurant.getOpeningTime());
			pstmt.setString(11, restaurant.getClosingTime());

			return pstmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Restaurant getRestaurantById(int restaurantId) {

		String sql =
				"SELECT * FROM restaurant WHERE restaurantid=?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, restaurantId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return mapRow(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Restaurant> getAllRestaurants() {

		List<Restaurant> restaurants = new ArrayList<>();

		String sql = "SELECT * FROM restaurant";

		try (Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				restaurants.add(mapRow(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return restaurants;
	}

	@Override
	public boolean updateRestaurant(Restaurant restaurant) {

		String sql =
				"UPDATE restaurant SET "
						+ "name=?, "
						+ "cuisinetype=?, "
						+ "deliverytime=?, "
						+ "address=?, "
						+ "rating=?, "
						+ "isactive=?, "
						+ "imagepath=?, "
						+ "description=?, "
						+ "priceRange=?, "
						+ "openingTime=?, "
						+ "closingTime=? "
						+ "WHERE restaurantid=?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, restaurant.getName());
			pstmt.setString(2, restaurant.getCuisineType());
			pstmt.setInt(3, restaurant.getDeliveryTime());
			pstmt.setString(4, restaurant.getAddress());
			pstmt.setDouble(5, restaurant.getRating());
			pstmt.setBoolean(6, restaurant.isActive());
			pstmt.setString(7, restaurant.getImagePath());

			pstmt.setString(8, restaurant.getDescription());
			pstmt.setString(9, restaurant.getPriceRange());
			pstmt.setString(10, restaurant.getOpeningTime());
			pstmt.setString(11, restaurant.getClosingTime());

			pstmt.setInt(12, restaurant.getRestaurantId());

			return pstmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean deleteRestaurant(int restaurantId) {

		String sql =
				"DELETE FROM restaurant WHERE restaurantid=?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, restaurantId);

			return pstmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private Restaurant mapRow(ResultSet rs) throws Exception {

		return new Restaurant(
				rs.getInt("restaurantid"),
				rs.getString("name"),
				rs.getString("cuisinetype"),
				rs.getInt("deliverytime"),
				rs.getString("address"),
				rs.getDouble("rating"),
				rs.getBoolean("isactive"),
				rs.getString("imagepath"),
				rs.getString("description"),
				rs.getString("priceRange"),
				rs.getString("openingTime"),
				rs.getString("closingTime"));
	}
}