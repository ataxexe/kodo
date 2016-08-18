package tools.devnull.kodo;

import java.util.function.Predicate;

/**
 * A set of useful predicates
 */
public interface Predicates {

  /**
   * Indicates that the value should be <code>true</code>
   */
  Predicate<Boolean> TRUE = value -> value;

  /**
   * Indicates that the value should be <code>false</code>
   */
  Predicate<Boolean> FALSE = value -> !value;

  /**
   * Indicates that the value should be <code>null</code>
   */
  Predicate NULL = obj -> obj == null;

  /**
   * Indicates that the value should not be <code>null</code>
   */
  Predicate NOT_NULL = obj -> obj != null;

  /**
   * Indicates a value that should be empty
   *
   * @see EmptyPredicate
   */
  Predicate EMPTY = new EmptyPredicate();

  /**
   * Indicates a value that should be empty
   *
   * @return a predicate that checks if the value is empty
   * @see EmptyPredicate
   */
  static <T> Predicate<T> empty() {
    return EMPTY;
  }

  /**
   * Predicate that returns <code>true</code> if the value is greather than
   * the given one
   */
  static Predicate<Comparable> greatherThan(Comparable value) {
    return comparable -> comparable.compareTo(value) > 0;
  }

  /**
   * Predicate that returns <code>true</code> if the value is greather or equal
   * than the given one
   */
  static Predicate<Comparable> greatherThanOrEqual(Comparable value) {
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

  /**
   * Predicate for evaluating iterable objects. Each object will be tested against the
   * given predicate and the result will be {@code true} only if all objects pass in
   * the evaluation.
   *
   * @param predicate the predicate to evaluate the objects
   * @return a predicate that uses the given one to test iterable objects
   */
  static <T> Predicate<Iterable<T>> eachOne(Predicate<? super T> predicate) {
    return iterable -> {
      boolean result = true;
      for (T t : iterable) {
        result = predicate.test(t);
      }
      return result;
    };
  }

}
