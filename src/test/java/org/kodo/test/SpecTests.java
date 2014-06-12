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

package org.kodo.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.kodo.Spec.*;

/**
 * @author Marcelo Guimarães
 */
public class SpecTests {

  private void test(Predicate predicate, Object value) {
    assertTrue(predicate.test(value));
  }

  private void testFail(Predicate predicate, Object value) {
    assertFalse(predicate.test(value));
  }

  @Test
  public void testBeWithObject() {
    test(be("test"), "test");
    testFail(be("test"), "");

    test(be(1), 1);
    testFail(be(1), 2);
  }

  @Test
  public void testNotBeWithObject() {
    testFail(notBe("test"), "test");
    test(notBe("test"), "");

    testFail(notBe(1), 1);
    test(notBe(1), 2);
  }

  @Test
  public void testEqual() {
    test(equal("test"), "test");
    testFail(equal("test"), "");

    test(equal(1), 1);
    testFail(equal(1), 2);
  }

  @Test
  public void testNotEqual() {
    testFail(notEqual("test"), "test");
    test(notEqual("test"), "");

    testFail(notEqual(1), 1);
    test(notEqual(1), 2);
  }

  @Test
  public void testBeWithPredicate() {
    test(be(value -> value.toString().length() > 5), "123456");
    test(be(value -> value.toString().length() > 5), 123456);
    test(be(value -> true), null);
    test(be(value -> true), "");
    test(be(value -> true), 1);

    testFail(be(value -> value.toString().length() > 5), "12345");
    testFail(be(value -> value.toString().length() > 5), 12345);
    testFail(be(value -> false), null);
    testFail(be(value -> false), "");
    testFail(be(value -> false), 1);
  }

  @Test
  public void testNotBeWithPredicate() {
    testFail(notBe(value -> value.toString().length() > 5), "123456");
    testFail(notBe(value -> value.toString().length() > 5), 123456);
    testFail(notBe(value -> true), null);
    testFail(notBe(value -> true), "");
    testFail(notBe(value -> true), 1);

    test(notBe(value -> value.toString().length() > 5), "12345");
    test(notBe(value -> value.toString().length() > 5), 12345);
    test(notBe(value -> false), null);
    test(notBe(value -> false), "");
    test(notBe(value -> false), 1);
  }

  @Test
  public void testNull() {
    test(NULL, null);
    testFail(NULL, "");

    test(NOT_NULL, "");
    testFail(NOT_NULL, null);
  }

  @Test
  public void testBeEmpty() {
    test(EMPTY, Collections.emptyList());
    test(EMPTY, Collections.emptySet());

    testFail(EMPTY, Arrays.asList(1, 2, 3));
    testFail(EMPTY, new HashSet(Arrays.asList(1, 2, 3)));
  }

  @Test
  public void testBeTrue() {
    test(TRUE, true);
    testFail(TRUE, false);
  }

  @Test
  public void testBeFalse() {
    test(FALSE, false);
    testFail(FALSE, true);
  }

  @Test
  public void testBeNull() {
    test(NULL, null);
    testFail(NULL, "string");
  }

  @Test
  public void testRaise() {
    test(raise(IllegalArgumentException.class), new IllegalArgumentException());
    test(raise(RuntimeException.class), new IllegalArgumentException());

    testFail(raise(IllegalArgumentException.class), null);
    testFail(raise(IllegalArgumentException.class), new RuntimeException());
  }

  @Test
  public void testSucceed() {
    test(succeed(), null);

    testFail(succeed(), new RuntimeException());
    testFail(succeed(), new IllegalArgumentException());
    testFail(succeed(), new IllegalArgumentException());
  }

  @Test
  public void testMatch() {
    test(match(is(nullValue())), null);
    testFail(match(is(nullValue())), "");
  }

  @Test
  public void testGreatherThan() {
    test(greatherThan(10), 11);
    testFail(greatherThan(10), 10);
    testFail(greatherThan(10), 9);
  }

  @Test
  public void testGreatherThanOrEqual() {
    test(greatherThanOrEqual(10), 11);
    test(greatherThanOrEqual(10), 10);
    testFail(greatherThanOrEqual(10), 9);
  }

  @Test
  public void testLessThan() {
    test(lessThan(10), 9);
    testFail(lessThan(10), 10);
    testFail(lessThan(10), 11);
  }

  @Test
  public void testLessThanOrEqual() {
    test(lessThanOrEqual(10), 9);
    test(lessThanOrEqual(10), 10);
    testFail(lessThanOrEqual(10), 11);
  }

}
