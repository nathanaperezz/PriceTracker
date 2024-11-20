package org.example.model;


import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;          // User's email address
    private List<Item> items;      // List of items the user is tracking

    public User(String email) {
        this.email = email;
        this.items = new ArrayList<>();
    }

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Item> getItems() { return items; }
    public void addItem(Item item) { this.items.add(item); }
}
