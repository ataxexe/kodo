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

import org.hamcrest.Matcher;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Helper class that contains usefull methods to create the assertions for the
 * fluent interface.
 *
 * @author Marcelo Guimarães
 */
public class Spec {

  private Spec() {

  }

  /**
   * Indicates that the value should
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  public <T> Predicate<T> be(T value) {
    return obj -> Objects.equals(obj, value);
  }

  /**
   * Indicates that the value should not
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  public <T> Predicate<T> notBe(T value) {
    return obj -> !Objects.equals(obj, value);
  }

  /**
   * @see #be(Object)
   */
  public <T> Predicate<T> equal(T value) {
    return be(value);
  }

  /**
   * @see #notBe(Object)
   */
  public <T> Predicate<T> notEqual(T value) {
    return notBe(value);
  }

  /**
   * Indicates that the value should {@link Predicate#test(Object) match} the
   * given predicate.
   */
  public <T> Predicate<T> be(Predicate<T> predicate) {
    return predicate;
  }

  /**
   * Indicates that the value should not {@link Predicate#test(Object) match}
   * the given predicate.
   */
  public <T> Predicate<T> notBe(Predicate<T> predicate) {
    return predicate.negate();
  }

  /**
   * Indicates that the target should have something that is tested with
   * the given predicate.
   *
   * @param predicate the predicate to test the target.
   * @return a consumer that uses the given predicate to test the target.
   */
  public <T> Predicate<T> have(Predicate<T> predicate) {
    return predicate;
  }

  /**
   * Indicates that the target should not have something that is tested with
   * the given predicate.
   *
   * @param predicate the predicate to test the target.
   * @return a consumer that uses the given predicate to test the target.
   */
  public <T> Predicate<T> notHave(Predicate<T> predicate) {
    return predicate.negate();
  }

  /**
   * Indicates that the value should match the given matcher.
   */
  public <T> Predicate<T> match(Matcher matcher) {
    return obj -> matcher.matches(obj);
  }

  /**
   * Indicates that the operation should throw the given exception.
   */
  public Predicate<? extends Throwable> raise(Class<? extends Throwable> exception) {
    return error ->
        error != null && exception.isAssignableFrom(error.getClass());
  }

  /**
   * Indicates that the operation should not throw any exceptions.
   */
  public Predicate<? extends Throwable> succeed() {
    return error -> error == null;
  }

  /**
   * Creates a new Spec
   *
   * @return the created Spec object
   */
  public static Spec should() {
    return new Spec();
  }

}
