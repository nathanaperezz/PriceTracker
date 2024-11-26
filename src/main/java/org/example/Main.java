//Nathan Perez
//Replace the fetchCurrentPrice method in PriceTrackerService with actual API calls to the eBay API.
//Store user and item data in a database instead of hardcoding it
//Deploy the application and set up a job scheduler (e.g., cron or Quartz) to run checkPrices() periodically.


package org.example;

import org.example.model.Item;
import org.example.model.User;
import org.example.service.PriceTrackerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {


        // Sample data
        User user1 = new User("user1@example.com");
        user1.addItem(new Item("156530644638", "Legos", "https://www.ebay.com/itm/156530644638?_skw=lego&itmmeta=01JD5XTCB5MY4FC75Z3FZ6X89K&hash=item2471f4169e:g:4zsAAOSw5r9nPP-X&itmprp=enc%3AAQAJAAAAwHoV3kP08IDx%2BKZ9MfhVJKkZG10OfrUxKShkofGn4uLxPnHsXUOwHZIpeAxJXrnmCD48PuOvCcyWdnNTCNItLVRqBMcAaDJcQ3IkuDgs75uSL4Jz1F5%2FH7kmX16f5KmX1jPRVD1UlslEGTOUniThZrs27gFh42Px4oNvwtwNE4aL%2BwtSF8j%2B15Bee%2FG8rZYtZ34NB3zBFJFwIBCkEb4qedTK0--XKMsDvfMJAWMaF%2FPsb5I2DJYzfPnv37KEIQPWPQ%3D%3D%7Ctkp%3ABk9SR9LF6b3pZA", 899.99, 800.00));

        User user2 = new User("user2@example.com");
        user2.addItem(new Item("156504347420", "Surfboard", "https://www.ebay.com/itm/156504347420?_skw=surfboard&itmmeta=01JD5XVWZSEJPPMNTVTKG22WH5&hash=item247062d31c:g:0P0AAOSw3sRnLvOk&itmprp=enc%3AAQAJAAAA4HoV3kP08IDx%2BKZ9MfhVJKkI0sq1arlFZy57N1edUpTa7nDJnm4FKOdSNzeMLSL7UxoNAjbAK0MHO3ExPpGzVp%2Bi0VBbN6hc1OWpUM1Y0XBhzoWDhqYlllh7YUHQGpCiOO69GeLONu728dMHhl%2BJMAyZeGQHjrwJa4H2wk79kxVP8GRuFqKUtP78iX3QVhUGwIIXrkfHOWGsD4IixIYH2uz5mAIHESF6hKoiwU7FFvRPagjkHL6rPgiYdMjyoiNmDrm1OvNm8vO0%2FPIofsPYJwY8%2B5RUUG8wIfdvxTK2iDjj%7Ctkp%3ABk9SR_7P773pZA", 199.99, 150.00));

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        // Run the price tracker
        PriceTrackerService trackerService = new PriceTrackerService();
        trackerService.checkPrices(users);
    }
}
