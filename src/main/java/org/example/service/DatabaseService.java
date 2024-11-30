package org.example.service;

import org.example.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/price_tracker_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();

        String query = "SELECT id, email, url, original_price, target_price FROM user_items";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String email = resultSet.getString("email");
                String url = resultSet.getString("url");
                double originalPrice = resultSet.getDouble("original_price");
                double targetPrice = resultSet.getDouble("target_price");

                // Create the Item object
                Item item = new Item(id, email, url, originalPrice, targetPrice);
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
