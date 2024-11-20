package org.example.model;

public class Item {
    private String id;         // eBay Item ID
    private String title;      // Item title
    private String url;        // Item URL
    private double price;      // Current price
    private double desiredPrice; // Price at which user wants notification

    public Item(String id, String title, String url, double price, double desiredPrice) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.price = price;
        this.desiredPrice = desiredPrice;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getDesiredPrice() { return desiredPrice; }
    public void setDesiredPrice(double desiredPrice) { this.desiredPrice = desiredPrice; }
}
