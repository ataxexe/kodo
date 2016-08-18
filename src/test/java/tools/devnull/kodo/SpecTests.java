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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tools.devnull.kodo.Spec.should;

/**
 * The test suite for {@link Spec}
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
    test(should().be("test"), "test");
    testFail(should().be("test"), "");

    test(should().be(1), 1);
    testFail(should().be(1), 2);
  }

  @Test
  public void testNotBeWithObject() {
    testFail(should().notBe("test"), "test");
    test(should().notBe("test"), "");

    testFail(should().notBe(1), 1);
    test(should().notBe(1), 2);
  }

  @Test
  public void testEqual() {
    test(should().equal("test"), "test");
    testFail(should().equal("test"), "");

    test(should().equal(1), 1);
    testFail(should().equal(1), 2);
  }

  @Test
  public void testNotEqual() {
    testFail(should().notEqual("test"), "test");
    test(should().notEqual("test"), "");

    testFail(should().notEqual(1), 1);
    test(should().notEqual(1), 2);
  }

  @Test
  public void testBeWithPredicate() {
    test(should().be(noMoreThan5Chars), "123456");
    test(should().be(noMoreThan5Chars), 123456);
    test(should().be(alwaysTrue), null);
    test(should().be(alwaysTrue), "");
    test(should().be(alwaysTrue), 1);

    testFail(should().be(noMoreThan5Chars), "12345");
    testFail(should().be(noMoreThan5Chars), 12345);
    testFail(should().be(alwaysFalse), null);
    testFail(should().be(alwaysFalse), "");
    testFail(should().be(alwaysFalse), 1);
  }

  @Test
  public void testNotBeWithPredicate() {
    testFail(should().notBe(noMoreThan5Chars), "123456");
    testFail(should().notBe(noMoreThan5Chars), 123456);
    testFail(should().notBe(alwaysTrue), null);
    testFail(should().notBe(alwaysTrue), "");
    testFail(should().notBe(alwaysTrue), 1);

    test(should().notBe(noMoreThan5Chars), "12345");
    test(should().notBe(noMoreThan5Chars), 12345);
    test(should().notBe(alwaysFalse), null);
    test(should().notBe(alwaysFalse), "");
    test(should().notBe(alwaysFalse), 1);
  }

  @Test
  public void testHaveWithPredicate() {
    test(should().have(noMoreThan5Chars), "123456");
    test(should().have(noMoreThan5Chars), 123456);
    test(should().have(alwaysTrue), null);
    test(should().have(alwaysTrue), "");
    test(should().have(alwaysTrue), 1);

    testFail(should().have(noMoreThan5Chars), "12345");
    testFail(should().have(noMoreThan5Chars), 12345);
    testFail(should().have(alwaysFalse), null);
    testFail(should().have(alwaysFalse), "");
    testFail(should().have(alwaysFalse), 1);
  }

  @Test
  public void testNotHaveWithPredicate() {
    testFail(should().notHave(noMoreThan5Chars), "123456");
    testFail(should().notHave(noMoreThan5Chars), 123456);
    testFail(should().notHave(alwaysTrue), null);
    testFail(should().notHave(alwaysTrue), "");
    testFail(should().notHave(alwaysTrue), 1);

    test(should().notHave(noMoreThan5Chars), "12345");
    test(should().notHave(noMoreThan5Chars), 12345);
    test(should().notHave(alwaysFalse), null);
    test(should().notHave(alwaysFalse), "");
    test(should().notHave(alwaysFalse), 1);
  }

  @Test
  public void testRaise() {
    test(should().raise(IllegalArgumentException.class), new IllegalArgumentException());
    test(should().raise(RuntimeException.class), new IllegalArgumentException());

    testFail(should().raise(IllegalArgumentException.class), null);
    testFail(should().raise(IllegalArgumentException.class), new RuntimeException());
  }

  @Test
  public void testSucceed() {
    test(should().succeed(), null);

    testFail(should().succeed(), new RuntimeException());
    testFail(should().succeed(), new IllegalArgumentException());
    testFail(should().succeed(), new IllegalArgumentException());
  }

  @Test
  public void testMatch() {
    test(should().match(is(nullValue())), null);
    testFail(should().match(is(nullValue())), "");
  }

}
