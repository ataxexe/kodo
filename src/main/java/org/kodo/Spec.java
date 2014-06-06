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
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.junit.Assert.*;

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
  public static Consumer<Boolean> TRUE = be(true);

  /**
   * Indicates that the value should be <code>false</code>
   */
  public static Consumer<Boolean> FALSE = be(false);

  /**
   * Indicates that the value should be <code>null</code>
   */
  public static Consumer NULL = obj -> assertNull(obj);

  /**
   * Indicates that the value should not be <code>null</code>
   */
  public static Consumer NOT_NULL = obj -> assertNotNull(obj);

  /**
   * Indicates that the value should be a collection and
   * {@link java.util.Collection#isEmpty() empty}
   */
  public static Consumer<Collection> EMPTY =
      collection -> assertTrue(collection.isEmpty());

  /**
   * Indicates that the value should
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  public static Consumer be(Object value) {
    return obj -> assertEquals(value, obj);
  }

  /**
   * Indicates that the value should
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  public static Consumer equal(Object value) {
    return be(value);
  }

  /**
   * Indicates that the value should not
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  public static Consumer notBe(Object value) {
    return obj -> assertNotEquals(value, obj);
  }

  /**
   * Indicates that the value should not
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  public static Consumer notEqual(Object value) {
    return notBe(value);
  }

  /**
   * Indicates that the value should {@link Predicate#test(Object) match} the
   * given predicate.
   */
  public static Consumer be(Predicate predicate) {
    return obj -> assertTrue(predicate.test(obj));
  }

  /**
   * Indicates that the value should not {@link Predicate#test(Object) match}
   * the given predicate.
   */
  public static Consumer notBe(Predicate predicate) {
    return obj -> assertFalse(predicate.test(obj));
  }

  /**
   * Indicates that the target should have something that is tested with
   * the given predicate.
   *
   * @param predicate the predicate to test the target.
   * @return a consumer that uses the given predicate to test the target.
   */
  public static <T> Consumer<T> have(Predicate<T> predicate) {
    return obj -> assertTrue(predicate.test(obj));
  }

  /**
   * Indicates that the target should not have something that is tested with
   * the given predicate.
   *
   * @param predicate the predicate to test the target.
   * @return a consumer that uses the given predicate to test the target.
   */
  public static <T> Consumer<T> notHave(Predicate<T> predicate) {
    return obj -> assertTrue(predicate.negate().test(obj));
  }

  /**
   * Indicates that the value should match the given matcher.
   */
  public static Consumer match(Matcher matcher) {
    return obj -> assertThat(obj, matcher);
  }

  /**
   * Indicates that the operation should throw the given exception.
   */
  public static Consumer raise(Class<? extends Throwable> exception) {
    return (error) -> {
      if (error != null) {
        assertTrue(exception.isAssignableFrom(error.getClass()));
      } else {
        throw new AssertionError("No Exception was thrown.");
      }
    };
  }

  /**
   * Indicates that the operation should not throw any exceptions.
   */
  public static Consumer succeed() {
    return (error) -> {
      if (error != null) {
        throw new AssertionError("Exception " +
            error.getClass().getCanonicalName() + " was thrown.");
      }
    };
  }

}
