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
 * A class to create scenarios for testing.
 *
 * @author Marcelo Guimarães
 * @see #given(Object)
 */
public class TestScenario<T> implements Scenario<T> {

  protected final T target;

  public TestScenario() {
    this(null);
  }

  public TestScenario(T target) {
    this.target = target;
  }

  private void test(Predicate predicate, Object target, String message) {
    if (!predicate.test(target)) {
      throw new AssertionError(message.isEmpty() ? defaultMessage(target) : message);
    }
  }

  private String defaultMessage(Object target) {
    return String.format("for value: %s", target);
  }

  @Override
  public Scenario<T> when(Consumer<? super T> operation) {
    operation.accept(target);
    return this;
  }

  @Override
  public Scenario<T> then(Consumer operation, Predicate test, String message) {
    try {
      operation.accept(target);
      test(test, null, message);
    } catch (Throwable t) {
      test(test, t, message);
    }
    return this;
  }

  @Override
  public Scenario<T> it(Predicate<? super T> test, String message) {
    test(test, target, message);
    return this;
  }

  @Override
  public Scenario<T> it(Predicate<? super T> test) {
    return it(test, "");
  }

  @Override
  public Scenario<T> when(Runnable operation) {
    operation.run();
    return this;
  }

  @Override
  public Scenario<T> then(Consumer<? super T> operation, Predicate<Throwable> test) {
    return then(operation, test, "");
  }

  @Override
  public <E> Scenario<T> then(Function<? super T, E> function, Predicate<? super E> test) {
    test(test, function.apply(target), "");
    return this;
  }

  @Override
  public <E> Scenario<T> then(Function<? super T, E> function, Predicate<? super E> test, String message) {
    test(test, function.apply(target), message);
    return this;
  }

  @Override
  public <E> Scenario<T> expect(E value, Predicate<E> test) {
    test(test, value, "");
    return this;
  }

  @Override
  public <E> Scenario<T> expect(E value, Predicate<E> test, String message) {
    test(test, value, message);
    return this;
  }

  /**
   * Start defining a new {@link Scenario} based on the given target.
   *
   * @param object the target object
   * @param <T>    the type of the target
   * @return a new {@link Scenario}.
   */
  public static <T> Scenario<T> given(T object) {
    return new TestScenario<>(object);
  }

}
