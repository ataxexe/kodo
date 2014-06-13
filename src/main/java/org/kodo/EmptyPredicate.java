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

import org.kodo.function.BasePredicate;
import org.kodo.function.Predicate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A predicate to test if an object is empty. Current this class supports the
 * given types:
 * <ul> <li>Collection - checks the {@link Collection#isEmpty()} method</li>
 * <li>Map - checks the {@link Map#isEmpty()} method</li> <li>CharSequence -
 * checks the {@link CharSequence#length()} method</li> <li>String - checks the
 * {@link String#isEmpty()} method</li> <li>Object[] - checks the length
 * variable</li> <li>boolean[] - checks the length variable</li> <li>byte[] -
 * checks the length variable</li> <li>char[] - checks the length variable</li>
 * <li>double[] - checks the length variable</li> <li>float[] - checks the
 * length variable</li> <li>int[] - checks the length variable</li> <li>long[] -
 * checks the length variable</li> <li>short[] - checks the length variable</li>
 * </ul>
 *
 * @author Marcelo Guimarães
 */
public class EmptyPredicate extends BasePredicate implements Predicate {

  private Map<Class, Predicate> predicates = new HashMap<Class, Predicate>();

  public EmptyPredicate() {
    register(Collection.class, new BasePredicate<Collection>() {
      public boolean test(Collection target) {
        return target.isEmpty();
      }
    });
    register(Map.class, new BasePredicate<Map>() {
      public boolean test(Map target) {
        return target.isEmpty();
      }
    });
    register(String.class, new BasePredicate<String>() {
      public boolean test(String target) {
        return target.length() == 0;
      }
    });
    register(CharSequence.class, new BasePredicate<CharSequence>() {
      public boolean test(CharSequence target) {
        return target.length() == 0;
      }
    });
    register(Object[].class, new BasePredicate<Object[]>() {
      public boolean test(Object[] target) {
        return target.length == 0;
      }
    });
    register(boolean[].class, new BasePredicate<boolean[]>() {
      public boolean test(boolean[] target) {
        return target.length == 0;
      }
    });
    register(byte[].class, new BasePredicate<byte[]>() {
      public boolean test(byte[] target) {
        return target.length == 0;
      }
    });
    register(char[].class, new BasePredicate<char[]>() {
      public boolean test(char[] target) {
        return target.length == 0;
      }
    });
    register(short[].class, new BasePredicate<short[]>() {
      public boolean test(short[] target) {
        return target.length == 0;
      }
    });
    register(int[].class, new BasePredicate<int[]>() {
      public boolean test(int[] target) {
        return target.length == 0;
      }
    });
    register(long[].class, new BasePredicate<long[]>() {
      public boolean test(long[] target) {
        return target.length == 0;
      }
    });
    register(float[].class, new BasePredicate<float[]>() {
      public boolean test(float[] target) {
        return target.length == 0;
      }
    });
    register(double[].class, new BasePredicate<double[]>() {
      public boolean test(double[] target) {
        return target.length == 0;
      }
    });
  }

  /**
   * Register a new predicate for checking objects of the given type.
   *
   * @param type      the type for using the given predicate
   * @param predicate the predicate to evaluate the target
   * @param <T>       the type of the target
   */
  public <T> void register(Class<T> type, Predicate<T> predicate) {
    predicates.put(type, predicate);
  }

  /**
   * Tests if the given object is empty by using its type to find a predicate
   * that can test it.
   *
   * @param value the target
   * @return {@code true} if the object is empty
   * @throws java.lang.IllegalArgumentException if the {@code value} if of a
   *                                            type that is not registered
   * @throws java.lang.NullPointerException     if the {@code value} is {@code
   *                                            null}
   */
  public boolean test(Object value) {
    return findPredicate(value).test(value);
  }

  private Predicate findPredicate(Object target) {
    Class<?> type = target.getClass();
    if (predicates.containsKey(type)) {
      return predicates.get(type);
    } else {
      for (Map.Entry<Class, Predicate> entry : predicates.entrySet()) {
        if (entry.getKey().isAssignableFrom(type)) {
          return entry.getValue();
        }
      }
    }
    throw new IllegalArgumentException("Could not found a predicate for " + type);
  }

}
