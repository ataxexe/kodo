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

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interface that defines a common set of methods to define test scenarios for
 * a target object.
 *
 * @author Marcelo Guimarães
 * @see org.kodo.TestScenario#given(Object)
 */
public interface Scenario<T> {

  /**
   * Defines something to do with the target.
   *
   * @param operation the operation to do with the target
   * @return a reference to this object.
   */
  Scenario<T> when(Consumer<? super T> operation);

  /**
   * Defines an operation that may throw an exception.
   *
   * @param operation the operation to do with the target
   * @param test      the test to do with the raised exception (if no exception
   *                  is thrown, a <code>null</code> value will be given to this
   *                  consumer
   * @return a reference to this object.
   * @see Spec#raise(Class)
   */
  default Scenario<T> then(Consumer<? super T> operation, Predicate<?> test) {
    return then(operation, test, "");
  }

  /**
   * Defines an operation that may throw an exception.
   *
   * @param operation the operation to do with the target
   * @param test      the test to do with the raised exception (if no exception
   *                  is thrown, a <code>null</code> value will be given to this
   *                  consumer
   * @param message   the message to throw if the test fail
   * @return a reference to this object.
   */
  Scenario<T> then(Consumer<? super T> operation, Predicate<?> test, String message);

  /**
   * Defines operations to execute as
   * {@link #then(java.util.function.Consumer, java.util.function.Predicate)}.
   *
   * @param consumer the operation to execute with the target object.
   * @param test     the test to do with the raised exception (if no exception
   *                 is thrown, a <code>null</code> value will be given to this
   *                 consumer
   * @return a reference to this object
   */
  default Scenario<T> and(Consumer<? super T> consumer, Predicate test) {
    return then(consumer, test);
  }

  /**
   * Defines operations to execute as
   * {@link #then(java.util.function.Consumer, java.util.function.Predicate)}.
   *
   * @param consumer the operation to execute with the target object.
   * @param test     the test to do with the raised exception (if no exception
   *                 is thrown, a <code>null</code> value will be given to this
   *                 consumer
   * @param message  the message to throw if the test fail
   * @return a reference to this object
   */
  default Scenario<T> and(Consumer<? super T> consumer, Predicate test, String message) {
    return then(consumer, test, message);
  }

  /**
   * Defines a test for some target operation that returns a value.
   *
   * @param function the operation to do with the target
   * @param test     the test to do with the value returned by the given
   *                 function
   * @return a reference to this object
   * @see Spec
   */
  default Scenario<T> the(Function<? super T, ?> function, Predicate<?> test) {
    return the(function, test, "");
  }

  /**
   * Defines a test for some target operation that returns a value.
   *
   * @param function the operation to do with the target
   * @param test     the test to do with the value returned by the given
   *                 function
   * @param message  the message to throw if the test fail
   * @return a reference to this object
   */
  Scenario<T> the(Function<? super T, ?> function, Predicate<?> test, String message);

  /**
   * Defines a test for each element of the target. This requires an
   * {@link java.lang.Iterable} target.
   *
   * @param test the test to do with the values in the targegt
   * @return a reference to this object.
   */
  default Scenario<T> each(Predicate test) {
    return each(test, "");
  }

  /**
   * Defines a test for each element of the target. This requires an
   * {@link java.lang.Iterable} target.
   *
   * @param test    the test to do with the values in the targegt
   * @param message the message to throw if the test fail
   * @return a reference to this object.
   */
  Scenario<T> each(Predicate test, String message);

  /**
   * Defines a test to do with the target.
   *
   * @param test the test to do with the target
   * @return a reference to this object
   */
  default Scenario<T> thenIt(Predicate<? super T> test) {
    return thenIt(test, "");
  }

  /**
   * Adds more tests to do with the target as the
   * {@link #thenIt(java.util.function.Predicate)} method.
   *
   * @param test the test to do with the target
   * @return a reference to this object
   */
  default Scenario<T> and(Predicate<? super T> test) {
    return thenIt(test);
  }

  /**
   * Defines a test to do with the target.
   *
   * @param test    the test to do with the target
   * @param message the message to throw if the test fail
   * @return a reference to this object
   */
  Scenario<T> thenIt(Predicate<? super T> test, String message);

  /**
   * Adds more tests to do with the target as the
   * {@link #thenIt(java.util.function.Predicate, String)} method.
   *
   * @param test    the test to do with the target
   * @param message the message to throw if the test fail
   * @return a reference to this object
   */
  default Scenario<T> and(Predicate<? super T> test, String message) {
    return thenIt(test, message);
  }

  /**
   * Defines a test to do with the target.
   *
   * @param test the test to do with the target
   * @return a reference to this object
   */
  default Scenario<T> it(Predicate<? super T> test) {
    return it(test, "");
  }

  /**
   * Defines a test to do with the target.
   *
   * @param test    the test to do with the target
   * @param message the message to throw if the test fail
   * @return a reference to this object
   */
  Scenario<T> it(Predicate<? super T> test, String message);

  /**
   * Defines a test to do with a value.
   *
   * @param value the value to test
   * @param test  the test to do with the value
   * @return a reference to this object
   */
  default Scenario<T> the(Object value, Predicate test) {
    return the(value, test, "");
  }

  /**
   * Defines a test to do with a value.
   *
   * @param value the value to test
   * @param test  the test to do with the value
   * @return a reference to this object
   */
  Scenario<T> the(Object value, Predicate test, String message);

  /**
   * Defines a test to do with a value.
   *
   * @param value the value to test
   * @param test  the test to do with the value
   * @return a reference to this object
   */
  default Scenario<T> then(Object value, Predicate test) {
    return then(value, test, "");
  }

  /**
   * Defines a test to do with a value.
   *
   * @param value   the value to test
   * @param test    the test to do with the value
   * @param message the message to throw if the test fail
   * @return a reference to this object
   */
  Scenario<T> then(Object value, Predicate test, String message);

  /**
   * Returns the given predicate. This method should be used to make the
   * code more readable.
   *
   * @param predicate the predicate
   * @return the given predicate.
   */
  public static <T> Predicate<T> should(Predicate<T> predicate) {
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
