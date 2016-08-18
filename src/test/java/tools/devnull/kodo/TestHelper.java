package tools.devnull.kodo;

import java.util.function.Predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestHelper {

  public static void test(Predicate predicate, Object value) {
    assertTrue(predicate.test(value));
  }

  public static void testFail(Predicate predicate, Object value) {
    assertFalse(predicate.test(value));
  }

}
