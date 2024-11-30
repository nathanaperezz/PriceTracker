package org.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemUtils {
    public static String extractItemIdFromUrl(String url) {
        // Regular expression to match the item ID in the URL format
        String regex = "https://www\\.ebay\\.com/itm/(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);  // The item ID captured in the first group
        }
        return null;  // Return null if the ID is not found in the URL
    }
}

