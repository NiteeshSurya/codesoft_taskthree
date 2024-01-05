import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
public class codesofttask3
{
        private static final String API_URL = "YOUR_API_ENDPOINT";

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the base currency code (e.g., USD): ");
            String baseCurrency = scanner.next().toUpperCase();
            System.out.print("Enter the target currency code (e.g., EUR): ");
            String targetCurrency = scanner.next().toUpperCase();
            double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);

            if (exchangeRate == -1) {
                System.out.println("Failed to fetch exchange rates. Exiting.");
                return;
            }
            System.out.print("Enter the amount to convert: ");
            double amountToConvert = scanner.nextDouble();
            double convertedAmount = amountToConvert * exchangeRate;
            System.out.println(amountToConvert + " " + baseCurrency + " is equal to " +
                    convertedAmount + " " + targetCurrency);

            scanner.close();
        }

        private static double getExchangeRate(String baseCurrency, String targetCurrency) {
            try {
                URL url = new URL(API_URL + "?base=" + baseCurrency + "&symbols=" + targetCurrency);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                String jsonString = response.toString();
                double exchangeRate = Double.parseDouble(jsonString.split(":")[1].replace("}", "").trim());

                return exchangeRate;
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
    }
