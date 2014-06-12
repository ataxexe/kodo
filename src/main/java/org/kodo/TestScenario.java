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
 * A class to create scenarios for testing.
 *
 * @author Marcelo Guimarães
 */
public class TestScenario<T> implements Scenario<T> {

  private final T target;

  public TestScenario(T target) {
    this.target = target;
  }

  @Override
  public Scenario<T> when(Consumer<? super T> operation) {
    return null;
  }

  @Override
  public Scenario<T> then(Consumer<? super T> operation, Predicate<?> test, String message) {
    return null;
  }

  @Override
  public Scenario<T> the(Function<? super T, ?> function, Predicate<?> test, String message) {
    return null;
  }

  @Override
  public Scenario<T> each(Predicate test, String message) {
    return null;
  }

  @Override
  public Scenario<T> thenIt(Predicate<? super T> test, String message) {
    return null;
  }

  @Override
  public Scenario<T> it(Predicate<? super T> test, String message) {
    return null;
  }

  @Override
  public Scenario<T> the(Object value, Predicate test, String message) {
    return null;
  }

  @Override
  public Scenario<T> then(Object value, Predicate test, String message) {
    return null;
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
