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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * A class that holds a set of predicates for empty definitions.
 *
 * @author Marcelo Guimarães
 */
public class EmptyPredicate implements Predicate {

  /**
   * A predicate that returns <code>true</code>
   * if the given Collection is empty
   */
  private final Predicate<Collection> collectionPredicate = Collection::isEmpty;

  /**
   * A predicate that returns <code>true</code>
   * if the given List is empty
   */
  private final Predicate<List> listPredicate = List::isEmpty;

  /**
   * A predicate that returns <code>true</code>
   * if the given Object array is empty
   */
  private final Predicate<Object[]> objectArrayPredicate = array -> array.length == 0;

  /**
   * A predicate that returns <code>true</code>
   * if the given Map is empty
   */
  private final Predicate<Map> mapPredicate = Map::isEmpty;

  /**
   * A predicate that returns <code>true</code>
   * if the given String is empty
   */
  private final Predicate<String> stringPredicate = String::isEmpty;

  /**
   * A predicate that returns <code>true</code>
   * if the given CharSequence is empty
   */
  private final Predicate<CharSequence> charSequencePredicate = seq -> seq.length() == 0;

  /**
   * A predicate that returns <code>true</code>
   * if the given boolean array is empty
   */
  private final Predicate<boolean[]> booleanArrayPredicate = array -> array.length == 0;

  /**
   * A predicate that returns <code>true</code>
   * if the given byte array is empty
   */
  private final Predicate<byte[]> byteArrayPredicate = array -> array.length == 0;

  /**
   * A predicate that returns <code>true</code>
   * if the given char array is empty
   */
  private final Predicate<char[]> charArrayPredicate = array -> array.length == 0;

  /**
   * A predicate that returns <code>true</code>
   * if the given short array is empty
   */
  private final Predicate<short[]> shortArrayPredicate = array -> array.length == 0;

  /**
   * A predicate that returns <code>true</code>
   * if the given int array is empty
   */
  private final Predicate<int[]> intArrayPredicate = array -> array.length == 0;

  /**
   * A predicate that returns <code>true</code>
   * if the given long array is empty
   */
  private final Predicate<long[]> longArrayPredicate = array -> array.length == 0;

  /**
   * A predicate that returns <code>true</code>
   * if the given float array is empty
   */
  private final Predicate<float[]> floatArrayPredicate = array -> array.length == 0;

  /**
   * A predicate that returns <code>true</code>
   * if the given double array is empty
   */
  private final Predicate<double[]> doubleArrayPredicate = array -> array.length == 0;

  private Map<Class, Predicate> predicates = new HashMap<>();

  public EmptyPredicate() {
    predicates.put(Collection.class, collectionPredicate);
    predicates.put(List.class, listPredicate);
    predicates.put(Object[].class, objectArrayPredicate);
    predicates.put(Map.class, mapPredicate);
    predicates.put(String.class, stringPredicate);
    predicates.put(CharSequence.class, charSequencePredicate);
    predicates.put(boolean[].class, booleanArrayPredicate);
    predicates.put(byte[].class, byteArrayPredicate);
    predicates.put(char[].class, charArrayPredicate);
    predicates.put(short[].class, shortArrayPredicate);
    predicates.put(int[].class, intArrayPredicate);
    predicates.put(long[].class, longArrayPredicate);
    predicates.put(float[].class, floatArrayPredicate);
    predicates.put(double[].class, doubleArrayPredicate);
  }

  @Override
  public boolean test(Object o) {
    return findPredicate(o).test(o);
  }

  private Predicate findPredicate(Object target) {
    if (target != null) {
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
    }
    return (o) -> false;
  }

}
