package com.DAO;

import java.util.List;
import com.model.Menu;

public interface MenuDAO {

	boolean addMenu(Menu menu);

	Menu getMenuById(int menuId);

	List<Menu> getAllMenus();

	List<Menu> getMenusByRestaurantId(int restaurantId);

	boolean updateMenu(Menu menu);

	boolean deleteMenu(int menuId);
}