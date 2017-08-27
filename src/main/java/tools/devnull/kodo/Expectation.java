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

import org.hamcrest.Matcher;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Helper class that contains useful methods to create the assertions for the
 * fluent interface.
 *
 * @author Marcelo Guimarães
 */
public class Expectation {

  private final Function<Predicate, Predicate> modifier;

  private Expectation(Function<Predicate, Predicate> modifier) {
    this.modifier = modifier;
  }

  private <T> Predicate<T> create(Predicate<T> predicate) {
    return modifier.apply(predicate);
  }

  /**
   * Indicates that the value should
   * {@link java.lang.Object#equals(Object) eq} the given value.
   */
  public <T> Predicate<T> be(T value) {
    return create(obj -> Objects.equals(obj, value));
  }

  /**
   * A predicate that tests if the object is null.
   *
   * @return a predicate that tests if the object is null.
   * @since 3.1
   */
  public <T> Predicate<T> beNull() {
    return create(Objects::isNull);
  }

  /**
   * A predicate that tests if the object is null.
   *
   * @return a predicate that tests if the object is null.
   * @since 3.4
   */
  public <T> Predicate<T> beNull(Class<T> type) {
    return create(Objects::isNull);
  }

  /**
   * @see #be(Object)
   */
  public <T> Predicate<T> eq(T value) {
    return be(value);
  }

  /**
   * Indicates that the value should {@link Predicate#test(Object) match} the
   * given predicate.
   */
  public <T> Predicate<T> be(Predicate<T> predicate) {
    return create(predicate);
  }

  /**
   * Indicates that the target should have something that is tested with
   * the given predicate.
   *
   * @param predicate the predicate to test the target.
   * @return a consumer that uses the given predicate to test the target.
   */
  public <T> Predicate<T> have(Predicate<T> predicate) {
    return create(predicate);
  }

  /**
   * Indicates that the value should match the given matcher.
   */
  public <T> Predicate<T> match(Matcher matcher) {
    return create(matcher::matches);
  }

  /**
   * Indicates that the operation should throw the given exception.
   */
  public Predicate<Throwable> raise(Class<? extends Exception> exception) {
    return create(error -> error != null && exception.isAssignableFrom(error.getClass()));
  }

  /**
   * Indicates that the operation should not throw any exception.
   */
  public Predicate<Exception> succeed() {
    return create(Objects::isNull);
  }

  /**
   * Indicates that the operation should fail by throwing any exception.
   *
   * @since 3.0
   */
  public Predicate<Exception> fail() {
    return not(succeed());
  }

  public <T> Predicate<T> follow(Consumer<SpecDefinition<T>> definition) {
    return t -> {
      definition.accept(Spec.given(t));
      return true;
    };
  }

  /**
   * Creates a new Expectation that will negates the given predicates.
   *
   * @return a new Expectation that negates every given predicate
   */
  public Expectation not() {
    return new Expectation(Predicate::negate);
  }

  /**
   * Returns the negate function of the given predicate.
   *
   * @param predicate the predicate to negate
   * @return the given predicate negated
   */
  public <T> Predicate<T> not(Predicate<T> predicate) {
    return predicate.negate();
  }

  /**
   * Creates a new Expectation
   *
   * @return the created Expectation object
   */
  public static Expectation to() {
    return new Expectation(Function.identity());
  }

  /**
   * Returns the given predicate.
   * <p>
   * Use this method to write readable code.
   *
   * @param predicate the predicate to use
   * @return the given predicate
   */
  public static <T> Predicate<T> to(Predicate<T> predicate) {
    return predicate;
  }

  /**
   * Wraps a function into a consumer in case you need to use a function
   * to test if it succeed or failed.
   *
   * @param function the function to execute
   * @return a consumer that uses the given function
   * @since 3.3
   */
  public static <T> Consumer<T> doing(Function<T, ?> function) {
    return function::apply;
  }

  /**
   * Returns a function that always returns the supplied value.
   * <p>
   * Use this to test the target object in a SpecDefinition:
   * <p>
   * <code>.expect(it(), to().be(myTest))</code>
   *
   * @return a function that always returns the supplied value.
   * @see Function#identity()
   * @since 3.0
   */
  public static <T> Function<? super T, T> it() {
    return Function.identity();
  }

  /**
   * Returns a supplier that always returns the given value.
   *
   * @param value the value to return
   * @return a supplier that always returns the given value
   * @since 3.3
   */
  public static <E> Supplier<E> the(E value) {
    return () -> value;
  }

  /**
   * Returns the given consumer. Use this to improve code readability.
   *
   * @param consumer the consumer to return
   * @return the given consumer
   */
  public static <T> Consumer<T> otherwise(Consumer<T> consumer) {
    return consumer;
  }

  /**
   * Helper method to throw an {@link AssertionError} using the given reason as a message.
   */
  public static <T> Consumer<T> because(String reason) {
    return obj -> {
      throw new AssertionError(reason);
    };
  }

  /**
   * Returns a consumer that throws an {@link AssertionError} based on the
   * given object.
   *
   * @param format the format to apply to the message
   *               (see {@link String#format(String, Object...)}), there will
   *               be only one parameter to the message
   * @return a consumer object
   * @since 3.2
   */
  public static Consumer throwAssertionError(String format) {
    return object -> {
      throw new AssertionError(String.format(format, object));
    };
  }

  /**
   * Returns a consumert that throws an {@link AssertionError} with the following
   * format: {@code "for value: %s"}.
   *
   * @return a consumer object
   * @since 3.2
   */
  public static Consumer throwAssertionError() {
    return throwAssertionError("for value: %s");
  }

}
