package sht;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests that the getDistance() method works correctly.
 * 
 * @author Ted Shaw
 */
public class TestGetDistance {

  /**
   * Test the getDistance() method.
   */
  @Test
  public void testGetDistance() {
    Michelangelo mike = new Michelangelo();

    assertEquals(5.0, mike.getDistance(0, 0, 3, 4), 0.00001);
    assertEquals(25.0, mike.getDistance(15, 20, 35, 5), 0.00001);
    assertEquals(5.0, mike.getDistance(1, 2, 4, 6), 0.00001);
  }

}