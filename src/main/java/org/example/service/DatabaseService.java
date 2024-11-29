package org.example.service;

import org.example.model.Item;
import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/price_tracker_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public List<User> getUsersWithItems() {
        List<User> users = new ArrayList<>();

        String query = "SELECT email, id, url, original_price, target_price FROM user_items";


        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                User user = findUserByEmail(users, email);
                if (user == null) {
                    user = new User(email);
                    users.add(user);
                }

                String itemId = resultSet.getString("id");
                String url = resultSet.getString("url");
                double originalPrice = resultSet.getDouble("original_price");
                double targetPrice = resultSet.getDouble("target_price");

                Item item = new Item(itemId, url, originalPrice, targetPrice);
                user.addItem(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    private User findUserByEmail(List<User> users, String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
