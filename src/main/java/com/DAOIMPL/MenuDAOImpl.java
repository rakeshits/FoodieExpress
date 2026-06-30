package com.DAOIMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.DAO.MenuDAO;
import com.model.Menu;
import com.util.DBConnection;

public class MenuDAOImpl implements MenuDAO {

	@Override
	public boolean addMenu(Menu menu) {

		String sql = "INSERT INTO menu(restaurantid,itemname,description,price,isavailable,imagepath,category) VALUES(?,?,?,?,?,?,?)";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, menu.getRestaurantId());
			pstmt.setString(2, menu.getItemName());
			pstmt.setString(3, menu.getDescription());
			pstmt.setDouble(4, menu.getPrice());
			pstmt.setBoolean(5, menu.isAvailable());
			pstmt.setString(6, menu.getImagePath());
			pstmt.setString(7, menu.getCategory());

			return pstmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Menu getMenuById(int menuId) {

		String sql = "SELECT * FROM menu WHERE menuid=?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, menuId);

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
	public List<Menu> getAllMenus() {

		List<Menu> menus = new ArrayList<>();

		String sql = "SELECT * FROM menu";

		try (Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				menus.add(mapRow(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return menus;
	}

	@Override
	public List<Menu> getMenusByRestaurantId(int restaurantId) {

		List<Menu> menus = new ArrayList<>();

		String sql = "SELECT * FROM menu WHERE restaurantid=?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, restaurantId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				menus.add(mapRow(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return menus;
	}

	@Override
	public boolean updateMenu(Menu menu) {

		String sql = "UPDATE menu SET restaurantid=?, itemname=?, description=?, price=?, isavailable=?, imagepath=?, category=? WHERE menuid=?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, menu.getRestaurantId());
			pstmt.setString(2, menu.getItemName());
			pstmt.setString(3, menu.getDescription());
			pstmt.setDouble(4, menu.getPrice());
			pstmt.setBoolean(5, menu.isAvailable());
			pstmt.setString(6, menu.getImagePath());
			pstmt.setString(7, menu.getCategory());
			pstmt.setInt(8, menu.getMenuId());

			return pstmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean deleteMenu(int menuId) {

		String sql = "DELETE FROM menu WHERE menuid=?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, menuId);

			return pstmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private Menu mapRow(ResultSet rs) throws Exception {

		return new Menu(
				rs.getInt("menuid"),
				rs.getInt("restaurantid"),
				rs.getString("itemname"),
				rs.getString("description"),
				rs.getDouble("price"),
				rs.getBoolean("isavailable"),
				rs.getString("imagepath"),
				rs.getString("category"));
	}
}