package nl.rocva.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {

  private final CalculatorService calculatorService = new CalculatorService();

  @Test
  public void testAdd() {
    int result = calculatorService.add(2, 3);
    assertEquals(5, result);
  }

  @Test
  public void testDivide() {
    int result = calculatorService.divide(6, 2);
    assertEquals(3, result);
  }

  @Test
  public void testDivideByZero() {
    try {
      calculatorService.divide(6, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot divide by zero", e.getMessage());
    }
  }

}
