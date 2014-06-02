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
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.junit.Assert.*;

/**
 * @author Marcelo Guimarães
 */
public class Should {

  private Should() {

  }

  public static Consumer<Boolean> BE_TRUE = beTrue();

  public static Consumer<Boolean> BE_FALSE = beFalse();

  public static Consumer BE_NULL = beNull();

  public static Consumer NOT_BE_NULL = notBeNull();

  public static Consumer<Collection> BE_EMPTY = beEmpty();

  public static Consumer<Collection> NOT_BE_EMPTY = notBeEmpty();

  public static Consumer<Boolean> beTrue() {
    return be(true);
  }

  public static Consumer<Boolean> beFalse() {
    return be(false);
  }

  public static Consumer beNull() {
    return (obj) -> assertNull(obj);
  }

  public static Consumer notBeNull() {
    return (obj) -> assertNotNull(obj);
  }

  public static Consumer be(Object value) {
    return (obj) -> assertEquals(value, obj);
  }

  public static Consumer notBe(Object value) {
    return (obj) -> assertNotEquals(value, obj);
  }

  public static Consumer be(Predicate predicate) {
    return (obj) -> assertTrue(predicate.test(obj));
  }

  public static Consumer notBe(Predicate predicate) {
    return (obj) -> assertFalse(predicate.test(obj));
  }

  public static Consumer<Collection> beEmpty() {
    return (collection) -> assertTrue(collection.isEmpty());
  }

  public static Consumer<Collection> notBeEmpty() {
    return (collection) -> assertFalse(collection.isEmpty());
  }

  public static Consumer raise(Class<? extends Throwable> exception) {
    return (error) -> {
      if (error != null) {
        assertTrue(exception.isAssignableFrom(error.getClass()));
      } else {
        throw new AssertionError("No Exception was thrown.");
      }
    };
  }

  public static Consumer succeed() {
    return (error) -> {
      if (error != null) {
        throw new AssertionError("Exception " +
            error.getClass().getCanonicalName() + " was thrown.");
      }
    };
  }

}
