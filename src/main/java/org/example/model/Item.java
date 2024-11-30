package org.example.model;

public class Item {
    private String id;
    private String email;
    private String url;
    private double originalPrice;
    private double targetPrice;

    public Item(String id, String email, String url, double originalPrice, double targetPrice) {
        this.id = id;
        this.email = email;
        this.url = url;
        this.originalPrice = originalPrice;
        this.targetPrice = targetPrice;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public double getTargetPrice() {
        return targetPrice;
    }
}
