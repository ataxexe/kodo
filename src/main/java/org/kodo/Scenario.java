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

import org.kodo.util.function.Consumer;
import org.kodo.util.function.Function;

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
   * @see Should#raise(Class)
   */
  Scenario<T> then(Consumer<? super T> operation, Consumer<?> test);

  /**
   * Defines operations to execute as
   * {@link #then(org.kodo.util.function.Consumer, org.kodo.util.function.Consumer)}.
   *
   * @param consumer the operation to execute with the target object.
   * @param test     the test to do with the raised exception (if no exception
   *                 is thrown, a <code>null</code> value will be given to this
   *                 consumer
   * @return a reference to this object
   */
  Scenario<T> and(Consumer<? super T> consumer, Consumer test);

  /**
   * Defines a test for some target operation that returns a value.
   *
   * @param function the operation to do with the target
   * @param test     the test to do with the value returned by the given
   *                 function
   * @return a reference to this object
   * @see Should
   */
  Scenario<T> the(Function<? super T, ?> function, Consumer<?> test);

  /**
   * Defines a test for each element of the target. This requires an
   * {@link java.lang.Iterable} target.
   *
   * @param test the test to do with the values in the targegt
   * @return a reference to this object.
   */
  Scenario<T> each(Consumer test);

  /**
   * Defines a test for each element of the target. This requires
   * an {@link java.lang.Iterable} target.
   *
   * @param type the type of the elements
   * @param test the test to do with the values in the target
   * @return a reference to this object.
   */
  <E> Scenario<T> each(Class<E> type, Consumer<? super E> test);

  /**
   * Defines a set of tests to do with the target.
   *
   * @param tests the tests to do with the target
   * @return a reference to this object.
   */
  Scenario<T> thenIt(Consumer<? super T> tests);

  /**
   * Defines operations to execute as {@link #when(org.kodo.util.function.Consumer)}
   * or {@link #thenIt(org.kodo.util.function.Consumer)}.
   *
   * @param consumer the operation to execute with the target object.
   * @return a reference to this object
   */
  Scenario<T> and(Consumer<? super T> consumer);

  /**
   * Defines a test to do with the target.
   *
   * @param tests
   * @return
   */
  Scenario<T> it(Consumer<? super T> tests);

  /**
   * Defines a test to do with a value.
   *
   * @param value the value to test
   * @param test  the test to do with the value
   * @return a reference to this object
   */
  Scenario<T> the(Object value, Consumer test);

  /**
   * Defines a test to do with a value.
   *
   * @param value the value to test
   * @param test  the test to do with the value
   * @return a reference to this object
   */
  Scenario<T> then(Object value, Consumer test);

}
