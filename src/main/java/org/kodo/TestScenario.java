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

import org.kodo.function.Consumer;
import org.kodo.function.Function;
import org.kodo.function.Predicate;

/**
 * A class to create scenarios for testing.
 *
 * @author Marcelo Guimarães
 */
public class TestScenario<T> implements Scenario<T> {

  private final T target;

  public TestScenario(T target) {
    this.target = target;
  }

  private void test(Predicate predicate, Object target, String message) {
    if (!predicate.test(target)) {
      throw new AssertionError(message);
    }
  }

  public Scenario<T> when(Consumer<? super T> operation) {
    operation.accept(target);
    return this;
  }

  public Scenario<T> then(Consumer operation, Predicate test, String message) {
    try {
      operation.accept(target);
      test(test, null, message);
    } catch (Throwable t) {
      test(test, t, message);
    }
    return this;
  }

  public Scenario<T> then(Consumer<? super T> operation, Predicate<?> test) {
    return then(operation, test, "");
  }

  public Scenario<T> and(Consumer<? super T> consumer, Predicate test) {
    return then(consumer, test);
  }

  public Scenario<T> the(Function function, Predicate test, String message) {
    test(test, function.apply(target), message);
    return this;
  }

  public Scenario<T> and(Consumer<? super T> consumer, Predicate test, String message) {
    return then(consumer, test, message);
  }

  public Scenario<T> the(Function<? super T, ?> function, Predicate<?> test) {
    return the(function, test, "");
  }

  public Scenario<T> each(Predicate test, String message) {
    Iterable iterable = (Iterable) target;
    for (Object obj : iterable) {
      test(test, obj, message);
    }
    return this;
  }

  public Scenario<T> each(Predicate test) {
    return each(test, "");
  }

  public Scenario<T> thenIt(Predicate<? super T> test, String message) {
    test(test, target, message);
    return this;
  }

  public Scenario<T> thenIt(Predicate<? super T> test) {
    return thenIt(test, "");
  }

  public Scenario<T> and(Predicate<? super T> test) {
    return thenIt(test);
  }

  public Scenario<T> and(Predicate<? super T> test, String message) {
    return thenIt(test, message);
  }

  public Scenario<T> it(Predicate<? super T> test, String message) {
    test(test, target, message);
    return this;
  }

  public Scenario<T> it(Predicate<? super T> test) {
    return it(test, "");
  }

  public Scenario<T> the(Object value, Predicate test, String message) {
    test(test, value, message);
    return this;
  }

  public Scenario<T> then(Object value, Predicate test, String message) {
    test(test, value, message);
    return this;
  }

  public Scenario<T> the(Object value, Predicate test) {
    return the(value, test, "");
  }

  public Scenario<T> then(Object value, Predicate test) {
    return then(value, test, "");
  }

  /**
   * Start defining a new {@link Scenario} based on the given target.
   *
   * @param object the target object
   * @param <T>    the type of the target
   * @return a new {@link Scenario}.
   */
  public static <T> Scenario<T> given(T object) {
    return new TestScenario<T>(object);
  }

}
