import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        WeatherService service = new WeatherService();
        try(Scanner sc = new Scanner(System.in)){
            System.out.println("Weather App - Get current weather info");
            System.out.println("Enter city name: ");
            String city = sc.nextLine();
            try {
                WeatherData data = service.getWeather(city);
                System.out.println("\n Weather Report:");
                System.out.println(data);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
