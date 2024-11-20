package org.example.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.Config;

public class EbayAPI {
    public static void main(String[] args) throws Exception {
        String token = Config.get("ebay.token");

        // API endpoint
        String url = "https://api.ebay.com/buy/browse/v1/item_summary/search?q=laptop";

        // Send GET request
//        String response = Request.get(url)
//                .addHeader("Authorization", "Bearer " + token)
//                .execute()
//                .returnContent()
//                .asString();

        // Parse JSON response
        ObjectMapper mapper = new ObjectMapper();
        //JsonNode root = mapper.readTree(response);
        //System.out.println(root.toPrettyString());
    }
}
