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
 * A class to create scenarios for testing.
 *
 * @author Marcelo Guimarães
 */
public class TestScenario<T> implements Scenario<T> {

  private final T target;

  public TestScenario(T target) {
    this.target = target;
  }

  public Scenario<T> when(Consumer<? super T> operation) {
    operation.accept(target);
    return this;
  }

  public Scenario<T> then(Consumer operation, Consumer test) {
    try {
      operation.accept(target);
      test.accept(null);
    } catch (Throwable t) {
      test.accept(t);
    }
    return this;
  }

  public Scenario<T> the(Function function, Consumer test) {
    Object result = function.apply(target);
    test.accept(result);
    return this;
  }

  public Scenario<T> each(Consumer test) {
    for (Object obj : (Iterable) target) {
      test.accept(obj);
    }
    return this;
  }

  public Scenario<T> and(Consumer<? super T> consumer, Consumer test) {
    return then(consumer, test);
  }

  public <E> Scenario<T> each(Class<E> type, Consumer<? super E> test) {
    return each(test);
  }

  public Scenario<T> thenIt(Consumer<? super T> tests) {
    return when(tests);
  }

  public Scenario<T> and(Consumer<? super T> consumer) {
    return when(consumer);
  }

  public Scenario<T> it(Consumer<? super T> tests) {
    return when(tests);
  }

  public Scenario<T> the(Object value, Consumer test) {
    test.accept(value);
    return this;
  }

  public Scenario<T> then(Object value, Consumer test) {
    test.accept(value);
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
    return new TestScenario<T>(object);
  }

}
