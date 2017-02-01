package tools.devnull.kodo;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tools.devnull.kodo.Predicates.greaterThan;
import static tools.devnull.kodo.Predicates.greaterThanOrEqual;
import static tools.devnull.kodo.Predicates.lessThan;
import static tools.devnull.kodo.Predicates.lessThanOrEqual;

/**
 * The test suite for the {@link Predicates}
 */
public class PredicatesTest {

  @Test
  public void testGreaterThan() {
    assertTrue(greaterThan(10).test(11));
    assertFalse(greaterThan(10).test(10));
    assertFalse(greaterThan(10).test(9));
  }

  @Test
  public void testGreaterThanOrEqual() {
    assertTrue(greaterThanOrEqual(10).test(11));
    assertTrue(greaterThanOrEqual(10).test(10));
    assertFalse(greaterThanOrEqual(10).test(9));
  }

  @Test
  public void testLessThan() {
    assertTrue(lessThan(10).test(9));
    assertFalse(lessThan(10).test(10));
    assertFalse(lessThan(10).test(11));
  }

  @Test
  public void testLessThanOrEqual() {
    assertTrue(lessThanOrEqual(10).test(9));
    assertTrue(lessThanOrEqual(10).test(10));
    assertFalse(lessThanOrEqual(10).test(11));
  }

}
