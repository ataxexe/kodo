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

package tools.devnull.kodo;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interface that defines a common set of methods to define test scenarios for
 * a target object.
 *
 * @author Marcelo Guimarães
 * @see Spec#given(Object)
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
   * Defines an operation to execute.
   *
   * @param operation the operation to execute
   * @return a reference to this object.
   */
  Scenario<T> when(Runnable operation);

  /**
   * Defines an operation that may throw an exception.
   *
   * @param operation the operation to do with the target
   * @param test      the test to execute with the raised exception (if no exception
   *                  is thrown, a <code>null</code> value will be given to this
   *                  test
   * @return a reference to this object.
   * @see Expectation#raise(Class)
   */
  default Scenario<T> expect(Consumer<? super T> operation, Predicate<Throwable> test) {
    return expect(operation, test, null);
  }

  /**
   * Defines an operation that may throw an exception.
   *
   * @param operation the operation to do with the target
   * @param test      the test to execute with the raised exception (if no exception
   *                  is thrown, a <code>null</code> value will be given to this
   *                  test
   * @param message   the message to throw if the test fails
   * @return a reference to this object.
   */
  Scenario<T> expect(Consumer<? super T> operation, Predicate<Throwable> test, String message);

  /**
   * Defines a test for some target operation that returns a value.
   *
   * @param function the operation to do with the target
   * @param test     the test to execute with the value returned by the given
   *                 function
   * @return a reference to this object
   * @see Expectation
   */
  default <E> Scenario<T> expect(Function<? super T, E> function, Predicate<? super E> test) {
    return expect(function, test, null);
  }

  /**
   * Defines a test for some target operation that returns a value.
   *
   * @param function the operation to do with the target
   * @param test     the test to execute with the value returned by the given
   *                 function
   * @param message  the message to throw if the test fails
   * @return a reference to this object
   */
  <E> Scenario<T> expect(Function<? super T, E> function, Predicate<? super E> test, String message);

}
