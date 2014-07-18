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

import org.junit.Test;

import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static tools.devnull.kodo.Spec.*;

/**
 * @author Marcelo Guimarães
 */
public class SpecTests {

  private Predicate noMoreThan5Chars =
      value -> value.toString().length() > 5;
  private Predicate alwaysTrue = value -> true;
  private Predicate alwaysFalse = value -> false;

  public static void test(Predicate predicate, Object value) {
    assertTrue(predicate.test(value));
  }

  public static void testFail(Predicate predicate, Object value) {
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
    test(be(noMoreThan5Chars), "123456");
    test(be(noMoreThan5Chars), 123456);
    test(be(alwaysTrue), null);
    test(be(alwaysTrue), "");
    test(be(alwaysTrue), 1);

    testFail(be(noMoreThan5Chars), "12345");
    testFail(be(noMoreThan5Chars), 12345);
    testFail(be(alwaysFalse), null);
    testFail(be(alwaysFalse), "");
    testFail(be(alwaysFalse), 1);
  }

  @Test
  public void testNotBeWithPredicate() {
    testFail(notBe(noMoreThan5Chars), "123456");
    testFail(notBe(noMoreThan5Chars), 123456);
    testFail(notBe(alwaysTrue), null);
    testFail(notBe(alwaysTrue), "");
    testFail(notBe(alwaysTrue), 1);

    test(notBe(noMoreThan5Chars), "12345");
    test(notBe(noMoreThan5Chars), 12345);
    test(notBe(alwaysFalse), null);
    test(notBe(alwaysFalse), "");
    test(notBe(alwaysFalse), 1);
  }

  @Test
  public void testHaveWithPredicate() {
    test(have(noMoreThan5Chars), "123456");
    test(have(noMoreThan5Chars), 123456);
    test(have(alwaysTrue), null);
    test(have(alwaysTrue), "");
    test(have(alwaysTrue), 1);

    testFail(have(noMoreThan5Chars), "12345");
    testFail(have(noMoreThan5Chars), 12345);
    testFail(have(alwaysFalse), null);
    testFail(have(alwaysFalse), "");
    testFail(have(alwaysFalse), 1);
  }

  @Test
  public void testNotHaveWithPredicate() {
    testFail(notHave(noMoreThan5Chars), "123456");
    testFail(notHave(noMoreThan5Chars), 123456);
    testFail(notHave(alwaysTrue), null);
    testFail(notHave(alwaysTrue), "");
    testFail(notHave(alwaysTrue), 1);

    test(notHave(noMoreThan5Chars), "12345");
    test(notHave(noMoreThan5Chars), 12345);
    test(notHave(alwaysFalse), null);
    test(notHave(alwaysFalse), "");
    test(notHave(alwaysFalse), 1);
  }

  @Test
  public void testNull() {
    test(NULL, null);
    testFail(NULL, "");

    test(NOT_NULL, "");
    testFail(NOT_NULL, null);
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

  @Test
  public void testHelpers() {
    assertSame(alwaysTrue, should(alwaysTrue));
    assertEquals("some reason", because("some reason"));
    assertEquals("description", otherwise("description"));
  }

}
