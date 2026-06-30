package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	private static final String URL      = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/food_app?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
	private static final String USERNAME = System.getenv().getOrDefault("DB_USER", "root");
	private static final String PASSWORD = System.getenv().getOrDefault("DB_PASS", "root1234@");
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
