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

import org.kodo.util.function.Predicate;

/**
 * Interface that holds {@link org.kodo.util.function.Predicate predicates} to deal
 * with {@link java.lang.Comparable} objects.
 *
 * @author Marcelo Guimarães
 */
public class ComparablePredicates {

  private ComparablePredicates() {

  }

  /**
   * Predicate that returns <code>true</code> if the value is the same as
   * the given one
   */
  public static Predicate<Comparable> equal(final Comparable value) {

    return new Predicate<Comparable>() {

      public boolean test(Comparable comparable) {
        return comparable.compareTo(value) == 0;
      }
    };

  }

  /**
   * Predicate that returns <code>true</code> if the value is greather than
   * the given one
   */
  public static Predicate<Comparable> greatherThan(final Comparable value) {
    return new Predicate<Comparable>() {
      public boolean test(Comparable comparable) {
        return comparable.compareTo(value) > 0;
      }
    };
  }

  /**
   * Predicate that returns <code>true</code> if the value is greather or equal
   * than the given one
   */
  public static Predicate<Comparable> greatherThanOrEqual(final Comparable value) {
    return new Predicate<Comparable>() {
      public boolean test(Comparable comparable) {
        return comparable.compareTo(value) >= 0;
      }
    };
  }

  /**
   * Predicate that returns <code>true</code> if the value is less than
   * the given one
   */
  public static Predicate<Comparable> lessThan(final Comparable value) {
    return new Predicate<Comparable>() {
      public boolean test(Comparable comparable) {
        return comparable.compareTo(value) < 0;
      }
    };
  }

  /**
   * Predicate that returns <code>true</code> if the value is less or equal
   * than the given one
   */
  public static Predicate<Comparable> lessThanOrEqual(final Comparable value) {
    return new Predicate<Comparable>() {
      public boolean test(Comparable comparable) {
        return comparable.compareTo(value) <= 0;
      }
    };
  }

}
