package nl.rocva.project.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import nl.rocva.project.service.WeatherService;

/**
 * Tests for the WeatherController using MockMvc and mocked WeatherService
 */
@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WeatherService weatherService;

  @BeforeEach
  public void setup() {
    // Configure the mock behavior
    when(weatherService.getCurrentTemperature("Amsterdam")).thenReturn(15);
    when(weatherService.isRaining("Amsterdam")).thenReturn(true);
    when(weatherService.getForecast("Amsterdam", 3))
        .thenReturn("The forecast for Amsterdam for the next 3 days: Rainy with temperatures around 15°C");

    // Configure behavior for invalid inputs
    when(weatherService.getCurrentTemperature("")).thenThrow(new IllegalArgumentException("City name cannot be empty"));
    when(weatherService.getForecast("Amsterdam", 0)).thenThrow(new IllegalArgumentException("Days must be positive"));
  }

  @Test
  public void testGetTemperature() throws Exception {
    // Test successful request
    mockMvc.perform(get("/api/weather/temperature/Amsterdam"))
        .andExpect(status().isOk())
        .andExpect(content().string("15"));

    // Verify the service method was called
    verify(weatherService).getCurrentTemperature("Amsterdam");

    // Test bad request
    mockMvc.perform(get("/api/weather/temperature/"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testIsRaining() throws Exception {
    // TODO : Implement this test
  }

  @Test
  public void testGetForecast() throws Exception {
    // TODO : Implement this test
  }
}