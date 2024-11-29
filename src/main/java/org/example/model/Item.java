package org.example.model;

public class Item {
    private String id;         // eBay Item ID
    private String url;        // Item URL
    private double price;      // Current price
    private double targetPrice; // Price at which user wants notification

    public Item(String id, String url, double price, double targetPrice) {
        this.id = id;
        this.url = url;
        this.price = price;
        this.targetPrice = targetPrice;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getDesiredPrice() { return targetPrice; }
    public void setDesiredPrice(double targetPrice) { this.targetPrice = targetPrice; }
}
