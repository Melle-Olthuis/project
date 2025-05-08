package nl.rocva.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.rocva.project.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
  // TODO : Test this controller

  @Autowired
  private final WeatherService weatherService;

  public WeatherController(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  @GetMapping("/temperature/{city}")
  public ResponseEntity<Integer> getTemperature(@PathVariable String city) {
    try {
      int temperature = weatherService.getCurrentTemperature(city);
      return ResponseEntity.ok(temperature);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/rain/{city}")
  public ResponseEntity<Boolean> isRaining(@PathVariable String city) {
    try {
      boolean isRaining = weatherService.isRaining(city);
      return ResponseEntity.ok(isRaining);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/forecast/{city}")
  public ResponseEntity<String> getForecast(
      @PathVariable String city,
      @RequestParam(defaultValue = "3") int days) {
    try {
      String forecast = weatherService.getForecast(city, days);
      return ResponseEntity.ok(forecast);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}