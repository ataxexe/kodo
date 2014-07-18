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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * A predicate to test if an object is empty. Current this class supports the
 * given types:
 * <p>
 * <ul>
 * <li>Collection - checks the {@link Collection#isEmpty()} method</li>
 * <li>Map - checks the {@link Map#isEmpty()} method</li>
 * <li>CharSequence - checks the {@link CharSequence#length()} method</li>
 * <li>String - checks the {@link String#isEmpty()} method</li>
 * <li>Object[] - checks the length variable</li>
 * <li>boolean[] - checks the length variable</li>
 * <li>byte[] - checks the length variable</li>
 * <li>char[] - checks the length variable</li>
 * <li>double[] - checks the length variable</li>
 * <li>float[] - checks the length variable</li>
 * <li>int[] - checks the length variable</li>
 * <li>long[] - checks the length variable</li>
 * <li>short[] - checks the length variable</li>
 * </ul>
 *
 * @author Marcelo Guimarães
 */
public class EmptyPredicate implements Predicate {

  private Map<Class, Predicate> predicates = new HashMap<>();

  public EmptyPredicate() {
    register(Collection.class, Collection::isEmpty);
    register(Map.class, Map::isEmpty);
    register(String.class, String::isEmpty);
    register(CharSequence.class, seq -> seq.length() == 0);
    register(Object[].class, array -> array.length == 0);
    register(boolean[].class, array -> array.length == 0);
    register(byte[].class, array -> array.length == 0);
    register(char[].class, array -> array.length == 0);
    register(short[].class, array -> array.length == 0);
    register(int[].class, array -> array.length == 0);
    register(long[].class, array -> array.length == 0);
    register(float[].class, array -> array.length == 0);
    register(double[].class, array -> array.length == 0);
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
  @Override
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
