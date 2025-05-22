package nl.rocva.project.service;

/**
 * Service to retrieve weather data
 * This is an exercise for students to learn about mocking in tests
 */
public interface WeatherService {

  /**
   * Get the current temperature for a city
   * 
   * @param city The city name
   * @return The current temperature in Celsius
   */
  int getCurrentTemperature(String city);

  /**
   * Check if it's currently raining in a city
   * 
   * @param city The city name
   * @return true if it's raining, false otherwise
   */
  boolean isRaining(String city);

  /**
   * Get a weather forecast description for the next few days
   * 
   * @param city The city name
   * @param days Number of days to forecast
   * @return A string describing the weather forecast
   */
  String getForecast(String city, int days);
}