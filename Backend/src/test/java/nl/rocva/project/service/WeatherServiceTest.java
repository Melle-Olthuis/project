package nl.rocva.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for the WeatherService
 * This demonstrates how to use Mockito for mocking
 */
@SpringBootTest
public class WeatherServiceTest {

  @Mock
  private WeatherService weatherService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetCurrentTemperature() {
    // Arrange
    when(weatherService.getCurrentTemperature("Amsterdam")).thenReturn(15);
    when(weatherService.getCurrentTemperature("Dubai")).thenReturn(35);
    when(weatherService.getCurrentTemperature(null))
        .thenThrow(new IllegalArgumentException("City name cannot be empty"));

    // Act & Assert
    assertEquals(15, weatherService.getCurrentTemperature("Amsterdam"));
    assertEquals(35, weatherService.getCurrentTemperature("Dubai"));

    // Verify that the method was called with the expected parameters
    verify(weatherService).getCurrentTemperature("Amsterdam");
    verify(weatherService).getCurrentTemperature("Dubai");

    // Test exception handling
    assertThrows(IllegalArgumentException.class, () -> {
      weatherService.getCurrentTemperature(null);
    });
  }

  @Test
  public void testIsRaining() {
    // TODO: Implement this test
  }

  @Test
  public void testGetForecast() {
    // TODO: Implement this test
  };
}