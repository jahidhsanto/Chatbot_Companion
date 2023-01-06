package SERVER;

import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Weather {
    public static void main(String[] args) throws Exception {
        // Get the API Key from https://openweathermap.org/
        String apiKey = "cb483d7b1e7f2414d09e935ca9e0882b";

        // Get the city name from the command line argument
        String cityName = "London";

        // Construct the URL for the OpenWeatherMap API
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey;

        // Create a URL object
        URL url = new URL(urlString);

        // Create an InputStream for the URL
        InputStream inputStream = url.openStream();

        // Create a BufferedReader to read the InputStream
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // Read the response line by line
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            // Print the response
            System.out.println(line);
        }

        // Close the reader
        bufferedReader.close();
    }
}