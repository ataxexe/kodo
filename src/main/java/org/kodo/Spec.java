/*
 * The MIT License
 *
 * Copyright (c) 2014 Marcelo Guimarães <ataxexe@gmail.com>
 *
 * ----------------------------------------------------------------------
 * Permission  is hereby granted, free of charge, to any person obtaining
 * a  copy  of  this  software  and  associated  documentation files (the
 * "Software"),  to  deal  in the Software without restriction, including
 * without  limitation  the  rights to use, copy, modify, merge, publish,
 * distribute,  sublicense,  and/or  sell  copies of the Software, and to
 * permit  persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this  permission  notice  shall be
 * included  in  all  copies  or  substantial  portions  of the Software.
 *                        -----------------------
 * THE  SOFTWARE  IS  PROVIDED  "AS  IS",  WITHOUT  WARRANTY OF ANY KIND,
 * EXPRESS  OR  IMPLIED,  INCLUDING  BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN  NO  EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM,  DAMAGES  OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT  OR  OTHERWISE,  ARISING  FROM,  OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE   OR   THE   USE   OR   OTHER   DEALINGS  IN  THE  SOFTWARE.
 */

package org.kodo;

import org.hamcrest.Matcher;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Helper class that contains usefull methods to create the assertions for the
 * fluent interface.
 *
 * @author Marcelo Guimarães
 */
public interface Spec {

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
   * Indicates that the value should be a collection and
   * {@link java.util.Collection#isEmpty() empty}
   */
  Predicate<Collection> EMPTY = collection -> collection.isEmpty();

  /**
   * Predicate that returns <code>true</code> if the value is the same as
   * the given one
   */
  static Predicate<Comparable> equal(Comparable value) {
    return comparable -> comparable.compareTo(value) == 0;
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
   * Indicates that the value should
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  static Predicate be(Object value) {
    return obj -> Objects.equals(obj, value);
  }

  /**
   * Indicates that the value should not
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  static Predicate notBe(Object value) {
    return obj -> !Objects.equals(obj, value);
  }

  /**
   * Indicates that the value should not
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  static Predicate notEqual(Object value) {
    return notBe(value);
  }

  /**
   * Indicates that the value should {@link Predicate#test(Object) match} the
   * given predicate.
   */
  static Predicate be(Predicate predicate) {
    return predicate;
  }

  /**
   * Indicates that the value should not {@link Predicate#test(Object) match}
   * the given predicate.
   */
  static Predicate notBe(Predicate predicate) {
    return predicate.negate();
  }

  /**
   * Indicates that the target should have something that is tested with
   * the given predicate.
   *
   * @param predicate the predicate to test the target.
   * @return a consumer that uses the given predicate to test the target.
   */
  static Predicate have(Predicate predicate) {
    return predicate;
  }

  /**
   * Indicates that the target should not have something that is tested with
   * the given predicate.
   *
   * @param predicate the predicate to test the target.
   * @return a consumer that uses the given predicate to test the target.
   */
  static Predicate notHave(Predicate predicate) {
    return predicate.negate();
  }

  /**
   * Indicates that the value should match the given matcher.
   */
  static Predicate match(Matcher matcher) {
    return obj -> matcher.matches(obj);
  }

  /**
   * Indicates that the operation should throw the given exception.
   */
  static Predicate raise(Class<? extends Throwable> exception) {
    return error ->
        error != null && exception.isAssignableFrom(error.getClass());
  }

  /**
   * Indicates that the operation should not throw any exceptions.
   */
  static Predicate succeed() {
    return error -> error == null;
  }

  /**
   * Returns the given predicate. This method should be used to make the
   * code more readable.
   *
   * @param predicate the predicate
   * @return the given predicate.
   */
  static <T> Predicate<T> should(Predicate<T> predicate) {
    return predicate;
  }

  /**
   * Helper method to improve code readability. It returns the given string.
   * <p>
   * Use it with the methods that takes a message.
   */
  static String because(String reason) {
    return reason;
  }

  /**
   * Helper method to improve code readability. It returns the given string.
   * <p>
   * Use it with the methods that takes a message.
   */
  static String otherwise(String description) {
    return description;
  }

}
