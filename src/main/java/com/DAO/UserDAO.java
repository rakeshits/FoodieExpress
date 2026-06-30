package com.DAO;

import com.model.User;

public interface UserDAO {

	boolean addUser(User user);
	User getUserById(int userId);
	User getUserByEmail(String email);
	boolean updateUser(User user);
	boolean deleteUser(int userId);
	User validateUser(String email, String password);
}