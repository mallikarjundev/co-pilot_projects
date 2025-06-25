public class WeatherData {
    private String cityName;
    private double temperature;
    private String weatherDescription;

    public WeatherData(String cityName, double temperature, String weatherDescription) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
    }

    public String getCityName() {
        return cityName;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    @Override
    public String toString() {
        return String.format("City: %s\n Temp: %.2f°C\n Weather: %s",
                cityName, temperature, weatherDescription);
    }
}
