package nl.rocva.project.service;

import org.springframework.stereotype.Service;

/**
 * Implementation of WeatherService
 * In a real application, this would connect to an external weather API
 */
@Service
public class WeatherServiceImpl implements WeatherService {
  // TODO : Test this service

  /**
   * In a real application, this method would call an external API
   * This implementation is simplified for demonstration purposes
   */
  @Override
  public int getCurrentTemperature(String city) {
    // This would normally be a REST API call to a weather service
    // For demonstration purposes, we'll return a fixed value based on city name
    if (city == null || city.isEmpty()) {
      throw new IllegalArgumentException("City name cannot be empty");
    }

    // Use hash code of the city name to simulate different temperatures
    return (Math.abs(city.hashCode() % 40));
  }

  /**
   * Determine if it's raining based on the city name
   */
  @Override
  public boolean isRaining(String city) {
    if (city == null || city.isEmpty()) {
      throw new IllegalArgumentException("City name cannot be empty");
    }

    // For simplicity, we'll say it's raining if the city name length is odd
    return city.length() % 2 != 0;
  }

  /**
   * Get a weather forecast description
   */
  @Override
  public String getForecast(String city, int days) {
    if (city == null || city.isEmpty()) {
      throw new IllegalArgumentException("City name cannot be empty");
    }

    if (days <= 0) {
      throw new IllegalArgumentException("Days must be positive");
    }

    if (days > 7) {
      throw new IllegalArgumentException("Forecast limited to maximum 7 days");
    }

    boolean willRain = isRaining(city);
    int temperature = getCurrentTemperature(city);

    return String.format("The forecast for %s for the next %d days: %s with temperatures around %d°C",
        city, days, willRain ? "Rainy" : "Sunny", temperature);
  }
}