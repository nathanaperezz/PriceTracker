//Nathan Perez

package org.example;

import org.example.service.PriceTrackerService;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;


public class Main {

    static final Logger logger = (Logger) LogManager.getLogger("myLogger");

    public static void main(String[] args) {
        PriceTrackerService trackerService = new PriceTrackerService();
        try {
            logger.info("checking prices");
            trackerService.checkPrices();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
