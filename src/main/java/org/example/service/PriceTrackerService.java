package org.example.service;

import org.example.model.Item;
import org.example.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class PriceTrackerService {

    private static String EBAY_API_ENDPOINT;
    private static String OAUTH_TOKEN;

    static {
        try {
            // Load configuration properties
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

    public void checkPrices(List<User> users) throws IOException {
        for (User user : users) {
            for (Item item : user.getItems()) {
                double currentPrice = fetchCurrentPrice(item.getId());
                if (currentPrice <= item.getDesiredPrice()) {
                    notifyUser(user.getEmail(), item, currentPrice);
                }
            }
        }
    }

    private double fetchCurrentPrice(String itemId) {
        try {
            // Build the URL for the eBay API request
            URL url = new URL(EBAY_API_ENDPOINT + itemId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + OAUTH_TOKEN);
            connection.setRequestProperty("Content-Type", "application/json");

            // Check the response code
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Read the response
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder jsonResponse = new StringBuilder();
                while (scanner.hasNext()) {
                    jsonResponse.append(scanner.nextLine());
                }
                scanner.close();

                // Extract price from JSON response
                String response = jsonResponse.toString();
                return parsePriceFromResponse(response);
            } else {
                System.err.println("Failed to fetch price. Response Code: " + connection.getResponseCode());
                return Double.MAX_VALUE; // Return a very high price to avoid false triggers
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Double.MAX_VALUE;
        }
    }

    private double parsePriceFromResponse(String jsonResponse) {
        // Simple parsing for demonstration. Use a library like Jackson or Gson in production.
        String priceMarker = "\"value\":\"";
        int startIndex = jsonResponse.indexOf(priceMarker) + priceMarker.length();
        int endIndex = jsonResponse.indexOf("\"", startIndex);

        if (startIndex > 0 && endIndex > startIndex) {
            String priceString = jsonResponse.substring(startIndex, endIndex);
            return Double.parseDouble(priceString);
        }

        System.err.println("Price not found in the response.");
        return Double.MAX_VALUE;
    }

    private void notifyUser(String email, Item item, double currentPrice) throws IOException {
        System.out.println("Sending notification to: " + email);
        System.out.println("Price drop detected for item: " + item.getTitle() +
                ". Current Price: $" + currentPrice + ", Desired Price: $" + item.getDesiredPrice());
        // Use NotificationService to send the email
        NotificationService.sendEmail(
                email,
                "Price Drop Alert: " + item.getTitle(),
                "The price for the item '" + item.getTitle() + "' has dropped to $" + currentPrice +
                        ". Visit the item here: " + item.getUrl()
        );
    }
}
