import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {

    private final String apikey = "72949c3eb28c424f3f2be12f37448229";

    public WeatherData getWeather(String city) throws Exception {
        String url= String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s",city, apikey);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            String json = response.body();
            return parseJson(json);
        } else {
            throw new Exception("City not found or API error.");
        }
    }

    public WeatherData parseJson(String json) {
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        String name = obj.get("name").getAsString();
        double temp = obj.getAsJsonObject("main").get("temp").getAsDouble();
        String desc = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        return new WeatherData(name, temp, desc);
    }
}
