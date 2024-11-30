package org.example.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.example.model.Item;
import org.json.JSONObject;
import org.example.utils.ItemUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class PriceTrackerService {

    private static final Logger logger = LogManager.getLogger(PriceTrackerService.class);

    private static String EBAY_API_ENDPOINT;
    private static String OAUTH_TOKEN;

    static {
        try {
            //load configuration properties
            Properties properties = new Properties();
            properties.load(PriceTrackerService.class.getClassLoader().getResourceAsStream("config.properties"));

            EBAY_API_ENDPOINT = properties.getProperty("ebay.api.endpoint");
            OAUTH_TOKEN = properties.getProperty("ebay.api.oauth.token");

            if (EBAY_API_ENDPOINT == null || OAUTH_TOKEN == null) {
                throw new RuntimeException("Missing required properties in config.properties.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file.", e);
        }
    }

    public void checkPrices() throws IOException {
        logger.info("Starting price check...");
        DatabaseService dbService = new DatabaseService();
        List<Item> items = dbService.getAllItems();

        for (Item item : items) {
            double currentPrice = fetchCurrentPrice(item.getUrl());
            if (currentPrice <= item.getTargetPrice()) {
                notifyUser(item.getEmail(), item, currentPrice);
            }
            logger.debug("Successfully fetched price data.");
        }
    }

    private double parsePriceFromResponse(String jsonResponse) {
        String priceMarker = "\"value\":\"";
        int startIndex = jsonResponse.indexOf(priceMarker) + priceMarker.length();
        int endIndex = jsonResponse.indexOf("\"", startIndex);

        if (startIndex > 0 && endIndex > startIndex) {
            String priceString = jsonResponse.substring(startIndex, endIndex);
            try {
                return Double.parseDouble(priceString);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing price value: " + priceString);
                logger.error("Error occurred while checking prices", e);
            }
        }

        System.err.println("Price not found in the response.");
        return Double.MAX_VALUE; //returns a higher price to avoid false notifications
    }

    public double fetchCurrentPrice(String url) {
        //extract item ID
        String itemId = ItemUtils.extractItemIdFromUrl(url);

        if (itemId == null) {
            System.err.println("Invalid URL: Item ID not found");
            return Double.MAX_VALUE;  // Return a default value or handle the error appropriately
        }

        logger.info("Using itemId: " + itemId);

        //eBay API request with itemId
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://api.ebay.com/buy/browse/v1/item/" + itemId)
                    .header("Authorization", "Bearer " + OAUTH_TOKEN)
                    .header("Accept", "application/json")
                    .asJson();

            if (response.getStatus() == 200) {
                // Successfully fetched the item data
                JSONObject jsonResponse = response.getBody().getObject();
                // Extract the price (adjust based on actual eBay API response structure)
                if (jsonResponse.has("price")) {
                    return jsonResponse.getJSONObject("price").getDouble("value");
                } else {
                    System.err.println("Price not found in response.");
                    return Double.MAX_VALUE;  // Handle error (price not found)
                }
            } else {
                // Handle API error responses
                System.err.println("Failed to fetch price. Response Code: " + response.getStatus());
                System.err.println("Error Response: " + response.getBody().toString());
                return Double.MAX_VALUE;  // uses higher value to avoid false notifications
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            return Double.MAX_VALUE; //uses higher value to avoid false notifications
        }
    }

    private void notifyUser(String email, Item item, double currentPrice) throws IOException {
        System.out.println("Sending notification to: " + email);
        System.out.println("Price drop detected. Current Price: $" + currentPrice + ", Target Price: $" + item.getTargetPrice());
        // use NotificationService to send the email
        NotificationService.sendEmail(
                email,
                "Price Drop Alert",
                "The price has dropped to $" + currentPrice + ". Visit the item here: " + item.getUrl()
        );
    }
}
