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
import org.kodo.util.function.Consumer;
import org.kodo.util.function.Predicate;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Helper class that contains usefull methods to create the assertions for the
 * fluent interface.
 *
 * @author Marcelo Guimarães
 */
public class Should {

  private Should() {
  }

  /**
   * Indicates that the value should be <code>true</code>
   */
  public static Consumer<Boolean> BE_TRUE = beTrue();

  /**
   * Indicates that the value should be <code>false</code>
   */
  public static Consumer<Boolean> BE_FALSE = beFalse();

  /**
   * Indicates that the value should be <code>null</code>
   */
  public static Consumer BE_NULL = beNull();

  /**
   * Indicates that the value should not be <code>null</code>
   */
  public static Consumer NOT_BE_NULL = notBeNull();

  /**
   * Indicates that the value should be a collection and
   * {@link java.util.Collection#isEmpty() empty}
   */
  public static Consumer<Collection> BE_EMPTY = beEmpty();

  /**
   * Indicates that the value should not be a collection and
   * {@link java.util.Collection#isEmpty() empty}
   */
  public static Consumer<Collection> NOT_BE_EMPTY = notBeEmpty();

  /**
   * Indicates that the value should be <code>true</code>
   */
  public static Consumer<Boolean> beTrue() {
    return be(true);
  }

  /**
   * Indicates that the value should be <code>false</code>
   */
  public static Consumer<Boolean> beFalse() {
    return be(false);
  }

  /**
   * Indicates that the value should be <code>null</code>
   */
  public static Consumer beNull() {
    return new Consumer() {
      public void accept(Object obj) {
        assertNull(obj);
      }
    };
  }

  /**
   * Indicates that the value should not be <code>null</code>
   */
  public static Consumer notBeNull() {
    return new Consumer() {
      public void accept(Object obj) {
        assertNotNull(obj);
      }
    };
  }

  /**
   * Indicates that the value should
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  public static Consumer be(final Object value) {
    return new Consumer() {
      public void accept(Object obj) {
        assertEquals(value, obj);
      }
    };
  }

  /**
   * Indicates that the value should
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  public static Consumer equal(Object value) {
    return be(value);
  }

  /**
   * Indicates that the value should not
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  public static Consumer notBe(final Object value) {
    return new Consumer() {
      public void accept(Object obj) {
        assertNotEquals(value, obj);
      }
    };
  }

  /**
   * Indicates that the value should not
   * {@link java.lang.Object#equals(Object) equal} the given value.
   */
  public static Consumer notEqual(Object value) {
    return notBe(value);
  }

  /**
   * Indicates that the value should {@link Predicate#test(Object) match} the
   * given predicate.
   */
  public static Consumer be(final Predicate predicate) {
    return new Consumer() {
      public void accept(Object obj) {
        assertTrue(predicate.test(obj));
      }
    };
  }

  /**
   * Indicates that the value should not {@link Predicate#test(Object) match}
   * the given predicate.
   */
  public static Consumer notBe(final Predicate predicate) {
    return new Consumer() {
      public void accept(Object obj) {
        assertFalse(predicate.test(obj));
      }
    };
  }

  /**
   * Indicates that the value should be a collection and
   * {@link java.util.Collection#isEmpty() empty}
   */
  public static Consumer<Collection> beEmpty() {
    return new Consumer<Collection>() {
      public void accept(Collection collection) {
        assertTrue(collection.isEmpty());
      }
    };
  }

  /**
   * Indicates that the value should not be a collection and
   * {@link java.util.Collection#isEmpty() empty}
   */
  public static Consumer<Collection> notBeEmpty() {
    return new Consumer<Collection>() {
      public void accept(Collection collection) {
        assertFalse(collection.isEmpty());
      }
    };
  }

  /**
   * Indicates that the value should match the given matcher.
   */
  public static Consumer match(final Matcher matcher) {
    return new Consumer() {
      public void accept(Object obj) {
        assertThat(obj, matcher);
      }
    };
  }

  /**
   * Indicates that the operation should throw the given exception.
   */
  public static Consumer raise(final Class<? extends Throwable> exception) {
    return new Consumer() {
      public void accept(Object result) {
        if (result != null) {
          assertTrue(exception.isAssignableFrom(result.getClass()));
        } else {
          throw new AssertionError("No Exception was thrown.");
        }
      }
    };
  }

  /**
   * Indicates that the operation should not throw any exceptions.
   */
  public static Consumer succeed() {
    return new Consumer() {
      public void accept(Object result) {
        if (result != null) {
          throw new AssertionError("Exception " +
              result.getClass().getCanonicalName() + " was thrown.");
        }
      }
    };
  }

}
