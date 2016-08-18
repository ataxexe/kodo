package tools.devnull.kodo;

import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.assertTrue;
import static tools.devnull.kodo.Predicates.FALSE;
import static tools.devnull.kodo.Predicates.NOT_NULL;
import static tools.devnull.kodo.Predicates.NULL;
import static tools.devnull.kodo.Predicates.TRUE;
import static tools.devnull.kodo.Predicates.greatherThan;
import static tools.devnull.kodo.Predicates.greatherThanOrEqual;
import static tools.devnull.kodo.Predicates.lessThan;
import static tools.devnull.kodo.Predicates.lessThanOrEqual;
import static tools.devnull.kodo.SpecTests.testFail;

/**
 * The test suite for the {@link Predicates}
 */
public class PredicatesTest {

  public static void test(Predicate predicate, Object value) {
    assertTrue(predicate.test(value));
  }

  @Test
  public void testNull() {
    test(NULL, null);
    testFail(NULL, "");

    test(NOT_NULL, "");
    testFail(NOT_NULL, null);
  }

  @Test
  public void testBeTrue() {
    test(TRUE, true);
    testFail(TRUE, false);
  }

  @Test
  public void testBeFalse() {
    test(FALSE, false);
    testFail(FALSE, true);
  }

  @Test
  public void testBeNull() {
    test(NULL, null);
    testFail(NULL, "string");
  }

  @Test
  public void testGreatherThan() {
    test(greatherThan(10), 11);
    testFail(greatherThan(10), 10);
    testFail(greatherThan(10), 9);
  }

  @Test
  public void testGreatherThanOrEqual() {
    test(greatherThanOrEqual(10), 11);
    test(greatherThanOrEqual(10), 10);
    testFail(greatherThanOrEqual(10), 9);
  }

  @Test
  public void testLessThan() {
    test(lessThan(10), 9);
    testFail(lessThan(10), 10);
    testFail(lessThan(10), 11);
  }

  @Test
  public void testLessThanOrEqual() {
    test(lessThanOrEqual(10), 9);
    test(lessThanOrEqual(10), 10);
    testFail(lessThanOrEqual(10), 11);
  }

}
