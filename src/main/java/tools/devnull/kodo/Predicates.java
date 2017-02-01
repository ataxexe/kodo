package tools.devnull.kodo;

import java.util.function.Predicate;

/**
 * A set of useful predicates
 */
public interface Predicates {

  /**
   * Predicate that returns <code>true</code> if the value is greater than
   * the given one
   */
  static Predicate<Comparable> greaterThan(Comparable value) {
    return comparable -> comparable.compareTo(value) > 0;
  }

  /**
   * Predicate that returns <code>true</code> if the value is greater or equal
   * than the given one
   */
  static Predicate<Comparable> greaterThanOrEqual(Comparable value) {
    return comparable -> comparable.compareTo(value) >= 0;
  }

  /**
   * Predicate that returns <code>true</code> if the value is less than
   * the given one
   */
  static Predicate<Comparable> lessThan(Comparable value) {
    return comparable -> comparable.compareTo(value) < 0;
  }

  /**
   * Predicate that returns <code>true</code> if the value is less or equal
   * than the given one
   */
  static Predicate<Comparable> lessThanOrEqual(Comparable value) {
    return comparable -> comparable.compareTo(value) <= 0;
  }

}
