package nl.rocva.project.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
  public int add(int a, int b) {
    return a + b;
  }

  public int subtract(int a, int b) {
    return a - b;
  }

  public int multiply(int a, int b) {
    return a * b;
  }

  public int divide(int a, int b) {
    if (b == 0) {
      throw new IllegalArgumentException("Cannot divide by zero");
    }
    return a / b;
  }

  // TODO test deze functie (kwadraat functie)
  public int square(int a) {
    return a * a;
  }

  // TODO test deze functie (wortel functie)
  public int squareRoot(int a) {
    if (a < 0) {
      throw new IllegalArgumentException("Cannot calculate square root of a negative number");
    }
    return (int) Math.sqrt(a);
  }

  // TODO test deze functie (macht functie)
  public int power(int base, int exponent) {
    if (exponent < 0) {
      throw new IllegalArgumentException("Exponent must be non-negative");
    }
    int result = 1;
    for (int i = 0; i < exponent; i++) {
      result *= base;
    }
    return result;
  }

}
