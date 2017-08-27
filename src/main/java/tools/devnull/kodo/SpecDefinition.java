/*
 * The MIT License
 *
 * Copyright (c) 2014 Marcelo "Ataxexe" Guimarães <ataxexe@devnull.tools>
 *
 * Permission  is hereby granted, free of charge, to any person obtaining
 * a  copy  of  this  software  and  associated  documentation files (the
 * "Software"),  to  deal  in the Software without restriction, including
 * without  limitation  the  rights to use, copy, modify, merge, publish,
 * distribute,  sublicense,  and/or  sell  copies of the Software, and to
 * permit  persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * The  above  copyright  notice  and  this  permission  notice  shall be
 * included  in  all  copies  or  substantial  portions  of the Software.
 *
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
import java.util.function.Supplier;

/**
 * Interface that defines a common set of methods to define a specification for a target
 * object.
 *
 * @author Marcelo Guimarães
 * @see Spec#given(Object)
 */
public interface SpecDefinition<T> {

  /**
   * Returns a new Spec based on the given object.
   *
   * @param object the new target
   * @param <R>    the type of the target
   * @return a new Spec definition
   */
  <R> SpecDefinition<R> given(R object);

  /**
   * Returns a new Spec based on the return of the given function
   * applied to this Spec's target
   *
   * @param function the function to apply
   * @param <R>      the type of the return
   * @return a new Spec definition
   */
  <R> SpecDefinition<R> given(Function<T, R> function);

  /**
   * Indicates that a new spec will begin.
   *
   * @return a new Spec object
   */
  SpecDefinition begin();

  /**
   * Sets the default operation to execute if an expectation fails.
   *
   * @param operation the operation to execute
   * @return a new Spec object
   */
  SpecDefinition<T> onFail(Consumer<?> operation);

  /**
   * Defines something to do with the target.
   *
   * @param operation the operation to do with the target
   * @return a reference to this object.
   */
  SpecDefinition<T> when(Consumer<? super T> operation);

  /**
   * Defines an operation to execute.
   *
   * @param operation the operation to execute
   * @return a reference to this object.
   */
  SpecDefinition<T> when(Runnable operation);

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
  SpecDefinition<T> expect(Consumer<? super T> operation, Predicate<Throwable> test);

  /**
   * Defines an operation that may throw an exception.
   *
   * @param operation the operation to do with the target
   * @param test      the test to execute with the raised exception (if no exception
   *                  is thrown, a <code>null</code> value will be given to this
   *                  test
   * @param consumer  the operation to do with the evaluated value
   * @return a reference to this object
   * @see Expectation#because(String)
   */
  SpecDefinition<T> expect(Consumer<? super T> operation, Predicate<Throwable> test, Consumer<Throwable> consumer);

  /**
   * Defines a test for some target operation that returns a value.
   *
   * @param function the operation to do with the target
   * @param test     the test to execute with the value returned by the given
   *                 function
   * @return a reference to this object
   * @see Expectation
   */
  <E> SpecDefinition<T> expect(Function<? super T, E> function, Predicate<? super E> test);

  /**
   * Defines a test for some target operation that returns a value.
   *
   * @param function the operation to do with the target
   * @param test     the test to execute with the value returned by the given
   *                 function
   * @param consumer the operation to do with the evaluated value
   * @return a reference to this object
   * @see Expectation#because(String)
   */
  <E> SpecDefinition<T> expect(Function<? super T, E> function, Predicate<? super E> test, Consumer<E> consumer);

  /**
   * Defines a test for a value returned by a supplier.
   *
   * @param supplier the supplier to obtain a value
   * @param test     the test to execute with the value returned by the given
   *                 function
   * @return a reference to this object
   * @see Expectation
   */
  <E> SpecDefinition<T> expect(Supplier<E> supplier, Predicate<? super E> test);

  /**
   * Defines a test for a value returned by a supplier.
   *
   * @param supplier the supplier to obtain a value
   * @param test     the test to execute with the value returned by the given
   *                 function
   * @param consumer the operation to do with the evaluated value
   * @return a reference to this object
   * @see Expectation#because(String)
   */
  <E> SpecDefinition<T> expect(Supplier<E> supplier, Predicate<? super E> test, Consumer<E> consumer);

  /**
   * Defines a test for some target operation that returns a boolean value. The
   * test will succeed if the return value is {@code true}.
   *
   * @param function the operation to do with the target
   * @return a reference to this object
   */
  SpecDefinition<T> expect(Function<? super T, Boolean> function);

  /**
   * Defines a test for some target operation that returns a boolean value. The
   * test will succeed if the return value is {@code true}.
   *
   * @param function the operation to do with the target
   * @param consumer the operation to do with the evaluated value
   * @return a reference to this object
   * @see Expectation#because(String)
   */
  SpecDefinition<T> expect(Function<? super T, Boolean> function, Consumer<Boolean> consumer);

  /**
   * Defines a test for a boolean value. The test will succeed if the value is
   * {@code true}.
   *
   * @param value the value to test
   * @return a reference to this object
   */
  SpecDefinition<T> expect(boolean value);

  /**
   * Defines a test for a boolean value. The test will succeed if the value is
   * {@code true}.
   *
   * @param value    the value to test
   * @param consumer the operation to do with the evaluated value
   * @return a reference to this object
   * @see Expectation#because(String)
   */
  SpecDefinition<T> expect(boolean value, Consumer<Boolean> consumer);

  /**
   * Splits the target object into smaller objects and passes each one to the given consumer.
   *
   * @param type     the type of the smaller object
   * @param splitter a function to split the target object
   * @param spec     the spec to execute
   * @return a reference to this object
   * @since 3.0
   */
  <E> SpecDefinition<T> each(Class<E> type, Function<T, Iterable<E>> splitter, Consumer<SpecDefinition<E>> spec);

  /**
   * Splits the target object into smaller objects and passes each one to the given consumer.
   * <p>
   * This method assumes that the target is an {@link Iterable}.
   *
   * @param type the type of the smaller object
   * @param spec the spec to execute
   * @return a reference to this object
   * @since 3.0
   */
  default <E> SpecDefinition<T> each(Class<E> type, Consumer<SpecDefinition<E>> spec) {
    return each(type, target -> (Iterable) target, spec);
  }

}
