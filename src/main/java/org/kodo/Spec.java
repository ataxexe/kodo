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

import org.hamcrest.Matcher;
import org.kodo.function.BasePredicate;
import org.kodo.function.Predicate;

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
   * Indicates that the value should be <code>true</code>
   */
  public static final Predicate<Boolean> TRUE = new BasePredicate<Boolean>() {
    public boolean test(Boolean target) {
      return target;
    }
  };

  /**
   * Indicates that the value should be <code>false</code>
   */
  public static final Predicate<Boolean> FALSE = new BasePredicate<Boolean>() {
    public boolean test(Boolean target) {
      return !target;
    }
  };

  /**
   * Indicates that the value should be <code>null</code>
   */
  public static final Predicate NULL = new BasePredicate() {
    public boolean test(Object target) {
      return target == null;
    }
  };

  /**
   * Indicates that the value should not be <code>null</code>
   */
  public static final Predicate NOT_NULL = new BasePredicate() {
    public boolean test(Object target) {
      return target != null;
    }
  };

  /**
   * Indicates a value that should be empty
   *
   * @see org.kodo.EmptyPredicate
   */
  public static final Predicate EMPTY = new EmptyPredicate();

  /**
   * Predicate that returns <code>true</code> if the value is the same as the
   * given one
   */
  public static final Predicate<Comparable> equal(final Comparable value) {
    return new BasePredicate<Comparable>() {
      public boolean test(Comparable target) {
        return target.compareTo(value) == 0;
      }
    };
  }

  /**
   * Predicate that returns <code>true</code> if the value is greather than the
   * given one
   */
  public static final Predicate<Comparable> greatherThan(final Comparable value) {
    return new BasePredicate<Comparable>() {
      public boolean test(Comparable target) {
        return target.compareTo(value) > 0;
      }
    };
  }

  /**
   * Predicate that returns <code>true</code> if the value is greather or equal
   * than the given one
   */
  public static final Predicate<Comparable> greatherThanOrEqual(final Comparable value) {
    return new BasePredicate<Comparable>() {
      public boolean test(Comparable target) {
        return target.compareTo(value) >= 0;
      }
    };
  }

  /**
   * Predicate that returns <code>true</code> if the value is less than the
   * given one
   */
  public static final Predicate<Comparable> lessThan(final Comparable value) {
    return new BasePredicate<Comparable>() {
      public boolean test(Comparable target) {
        return target.compareTo(value) < 0;
      }
    };
  }

  /**
   * Predicate that returns <code>true</code> if the value is less or equal than
   * the given one
   */
  public static final Predicate<Comparable> lessThanOrEqual(final Comparable value) {
    return new BasePredicate<Comparable>() {
      public boolean test(Comparable target) {
        return target.compareTo(value) <= 0;
      }
    };
  }

  /**
   * Indicates that the value should {@link java.lang.Object#equals(Object)
   * equal} the given value.
   */
  public static final Predicate be(final Object value) {
    return new BasePredicate() {
      public boolean test(Object target) {
        return (target == value) || (target != null && target.equals(value));
      }
    };
  }

  /**
   * Indicates that the value should not {@link java.lang.Object#equals(Object)
   * equal} the given value.
   */
  public static final Predicate notBe(Object value) {
    return be(value).negate();
  }

  /**
   * Indicates that the value should not {@link java.lang.Object#equals(Object)
   * equal} the given value.
   */
  public static final Predicate notEqual(Object value) {
    return notBe(value);
  }

  /**
   * Indicates that the value should {@link Predicate#test(Object) match} the
   * given predicate.
   */
  public static final Predicate be(Predicate predicate) {
    return predicate;
  }

  /**
   * Indicates that the value should not {@link Predicate#test(Object) match}
   * the given predicate.
   */
  public static final Predicate notBe(Predicate predicate) {
    return predicate.negate();
  }

  /**
   * Indicates that the target should have something that is tested with the
   * given predicate.
   *
   * @param predicate the predicate to test the target.
   * @return a consumer that uses the given predicate to test the target.
   */
  public static final Predicate have(Predicate predicate) {
    return predicate;
  }

  /**
   * Indicates that the target should not have something that is tested with the
   * given predicate.
   *
   * @param predicate the predicate to test the target.
   * @return a consumer that uses the given predicate to test the target.
   */
  public static final Predicate notHave(Predicate predicate) {
    return predicate.negate();
  }

  /**
   * Indicates that the value should match the given matcher.
   */
  public static final Predicate match(final Matcher matcher) {
    return new BasePredicate() {
      public boolean test(Object target) {
        return matcher.matches(target);
      }
    };
  }

  /**
   * Indicates that the operation should throw the given exception.
   */
  public static final Predicate raise(final Class<? extends Throwable> exception) {
    return new BasePredicate() {
      public boolean test(Object error) {
        return error != null && exception.isAssignableFrom(error.getClass());
      }
    };
  }

  /**
   * Indicates that the operation should not throw any exceptions.
   */
  public static final Predicate succeed() {
    return new BasePredicate() {
      public boolean test(Object error) {
        return error == null;
      }
    };
  }

  /**
   * Returns the given predicate. This method should be used to make the code
   * more readable.
   *
   * @param predicate the predicate
   * @return the given predicate.
   */
  public static final <T> Predicate<T> should(Predicate<T> predicate) {
    return predicate;
  }

  /**
   * Helper method to improve code readability. It returns the given string.
   * <p/>
   * Use it with the methods that takes a message.
   */
  public static final String because(String reason) {
    return reason;
  }

  /**
   * Helper method to improve code readability. It returns the given string.
   * <p/>
   * Use it with the methods that takes a message.
   */
  public static final String otherwise(String description) {
    return description;
  }

}
