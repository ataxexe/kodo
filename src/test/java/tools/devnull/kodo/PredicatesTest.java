package tools.devnull.kodo;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tools.devnull.kodo.Predicates.FALSE;
import static tools.devnull.kodo.Predicates.NOT_NULL;
import static tools.devnull.kodo.Predicates.NULL;
import static tools.devnull.kodo.Predicates.TRUE;
import static tools.devnull.kodo.Predicates.greaterThan;
import static tools.devnull.kodo.Predicates.greaterThanOrEqual;
import static tools.devnull.kodo.Predicates.lessThan;
import static tools.devnull.kodo.Predicates.lessThanOrEqual;

/**
 * The test suite for the {@link Predicates}
 */
public class PredicatesTest {

  @Test
  public void testNull() {
    assertTrue(NULL.test(null));
    assertFalse(NULL.test(""));

    assertTrue(NOT_NULL.test(""));
    assertFalse(NOT_NULL.test(null));
  }

  @Test
  public void testBeTrue() {
    assertTrue(TRUE.test(true));
    assertFalse(TRUE.test(false));
  }

  @Test
  public void testBeFalse() {
    assertTrue(FALSE.test(false));
    assertFalse(FALSE.test(true));
  }

  @Test
  public void testBeNull() {
    assertTrue(NULL.test(null));
    assertFalse(NULL.test("string"));
  }

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
