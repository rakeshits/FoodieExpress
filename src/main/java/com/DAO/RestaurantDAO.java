package com.DAO;

import java.util.List;
import com.model.Restaurant;

public interface RestaurantDAO {

	boolean addRestaurant(Restaurant restaurant);

	Restaurant getRestaurantById(int restaurantId);

	List<Restaurant> getAllRestaurants();

	boolean updateRestaurant(Restaurant restaurant);

	boolean deleteRestaurant(int restaurantId);
}