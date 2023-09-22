package main.java;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) throws JSONException, IOException {
        // Create a HashMap to store currency codes and their corresponding numeric values
        //Hashmap is used to store key-value pairs. The key is used to uniquely identify the value within the map.
        HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();

        // Add currency codes and their numeric values to the HashMap
        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "EUR");
        currencyCodes.put(3, "BRL");
        currencyCodes.put(4, "CAD");

        // Declare variables to store the currency codes and amount
        String fromCode;
        String toCode;
        double amount;

        // Create a new Scanner object to read user input from the console
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Currency Converter App!");
        System.out.println("Currency converting from:?");
        System.out.println("1:USD\t 2:EUR\t 3:BRL\t 4:CAD");

        // Get the user's choice for converting from currency
        fromCode = currencyCodes.get(sc.nextInt());

        System.out.println("Currency converting to:?");
        System.out.println("1:USD\t 2:EUR\t 3:BRL\t 4:CAD");
        // Get the user's choice for converting to currency
        toCode = currencyCodes.get(sc.nextInt());

        System.out.println("What is the amount you wish to convert?");
        // Get the amount to be converted
        amount = sc.nextFloat();

        // Call the method to send HTTP GET request to the currency API
        sendHttpGetRequest(fromCode, toCode, amount);

        System.out.println("Thank you for using the Currency Converter App!");
    }

    static String sendHttpGetRequest(String fromCode, String toCode, double amount) throws IOException, JSONException {
        // API access key for currency conversion API
        String accessKey = "fca_live_AbvcBkmcv30gKU5KmIFd5eUQritJdPBc8QDiEaiU";

        // Construct the URL for API call with "fromCode" as the base currency and "toCode" as the currency to convert to
        String GET_URL = "https://api.freecurrencyapi.com/v1/latest?apikey=" + accessKey + "&currencies=" + fromCode + "&base_currency=" + toCode;

        // Create a URL object with the constructed URL
        URL url = new URL(GET_URL);

        // Open a connection to the API URL
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        // Set the request method for the connection as GET
        httpURLConnection.setRequestMethod("GET");
        // Get the response code from the API server
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // Success: Fetch the exchange rate for converting from "fromCode" to "toCode"
            // Create a BufferedReader to read the API response
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            // Read the API response line by line and append it to the StringBuffer
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the API response as a JSON object
            JSONObject obj = new JSONObject(response.toString());

            // Extract the exchange rate for converting from "fromCode" to "toCode"
            Double exchangeRate = obj.getJSONObject("data").getDouble(fromCode);

            // Display the retrieved JSON data (for debugging purposes)
            System.out.println(obj.getJSONObject("data"));

            // Display the exchange rate (for debugging purposes)
            System.out.println(exchangeRate);

            System.out.println();

            // Perform the currency conversion and display the result
            System.out.println(amount + " " + fromCode + " = " + amount / exchangeRate + " " + toCode);
        } else {
            System.out.println("GET request failed!");
        }
        return accessKey;
    }
}