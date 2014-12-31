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

package tools.devnull.kodo.test;

import org.junit.Test;

import tools.devnull.kodo.function.BasePredicate;
import tools.devnull.kodo.function.Predicate;
import tools.devnull.kodo.Spec;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Marcelo Guimarães
 */
public class SpecTests {

  private Predicate stringWithMoreThan5Chars = new BasePredicate() {
    public boolean test(Object value) {
      return value.toString().length() > 5;
    }
  };
  private Predicate alwaysTrue = new BasePredicate() {
    public boolean test(Object target) {
      return true;
    }
  };
  private Predicate alwaysFalse = new BasePredicate() {
    public boolean test(Object target) {
      return false;
    }
  };

  public static void test(Predicate predicate, Object value) {
    assertTrue(predicate.test(value));
  }

  public static void testFail(Predicate predicate, Object value) {
    assertFalse(predicate.test(value));
  }

  @Test
  public void testBeWithObject() {
    test(Spec.be("test"), "test");
    testFail(Spec.be("test"), "");

    test(Spec.be(1), 1);
    testFail(Spec.be(1), 2);
  }

  @Test
  public void testNotBeWithObject() {
    testFail(Spec.notBe("test"), "test");
    test(Spec.notBe("test"), "");

    testFail(Spec.notBe(1), 1);
    test(Spec.notBe(1), 2);
  }

  @Test
  public void testEqual() {
    test(Spec.equal("test"), "test");
    testFail(Spec.equal("test"), "");

    test(Spec.equal(1), 1);
    testFail(Spec.equal(1), 2);
  }

  @Test
  public void testNotEqual() {
    testFail(Spec.notEqual("test"), "test");
    test(Spec.notEqual("test"), "");

    testFail(Spec.notEqual(1), 1);
    test(Spec.notEqual(1), 2);
  }

  @Test
  public void testBeWithPredicate() {
    test(Spec.be(stringWithMoreThan5Chars), "123456");
    test(Spec.be(stringWithMoreThan5Chars), 123456);
    test(Spec.be(alwaysTrue), null);
    test(Spec.be(alwaysTrue), "");
    test(Spec.be(alwaysTrue), 1);

    testFail(Spec.be(stringWithMoreThan5Chars), "12345");
    testFail(Spec.be(stringWithMoreThan5Chars), 12345);
    testFail(Spec.be(alwaysFalse), null);
    testFail(Spec.be(alwaysFalse), "");
    testFail(Spec.be(alwaysFalse), 1);
  }

  @Test
  public void testNotBeWithPredicate() {
    testFail(Spec.notBe(stringWithMoreThan5Chars), "123456");
    testFail(Spec.notBe(stringWithMoreThan5Chars), 123456);
    testFail(Spec.notBe(alwaysTrue), null);
    testFail(Spec.notBe(alwaysTrue), "");
    testFail(Spec.notBe(alwaysTrue), 1);

    test(Spec.notBe(stringWithMoreThan5Chars), "12345");
    test(Spec.notBe(stringWithMoreThan5Chars), 12345);
    test(Spec.notBe(alwaysFalse), null);
    test(Spec.notBe(alwaysFalse), "");
    test(Spec.notBe(alwaysFalse), 1);
  }

  @Test
  public void testHaveWithPredicate() {
    test(Spec.have(stringWithMoreThan5Chars), "123456");
    test(Spec.have(stringWithMoreThan5Chars), 123456);
    test(Spec.have(alwaysTrue), null);
    test(Spec.have(alwaysTrue), "");
    test(Spec.have(alwaysTrue), 1);

    testFail(Spec.have(stringWithMoreThan5Chars), "12345");
    testFail(Spec.have(stringWithMoreThan5Chars), 12345);
    testFail(Spec.have(alwaysFalse), null);
    testFail(Spec.have(alwaysFalse), "");
    testFail(Spec.have(alwaysFalse), 1);
  }

  @Test
  public void testNotHaveWithPredicate() {
    testFail(Spec.notHave(stringWithMoreThan5Chars), "123456");
    testFail(Spec.notHave(stringWithMoreThan5Chars), 123456);
    testFail(Spec.notHave(alwaysTrue), null);
    testFail(Spec.notHave(alwaysTrue), "");
    testFail(Spec.notHave(alwaysTrue), 1);

    test(Spec.notHave(stringWithMoreThan5Chars), "12345");
    test(Spec.notHave(stringWithMoreThan5Chars), 12345);
    test(Spec.notHave(alwaysFalse), null);
    test(Spec.notHave(alwaysFalse), "");
    test(Spec.notHave(alwaysFalse), 1);
  }

  @Test
  public void testNull() {
    test(Spec.NULL, null);
    testFail(Spec.NULL, "");

    test(Spec.NOT_NULL, "");
    testFail(Spec.NOT_NULL, null);
  }

  @Test
  public void testBeTrue() {
    test(Spec.TRUE, true);
    testFail(Spec.TRUE, false);
  }

  @Test
  public void testBeFalse() {
    test(Spec.FALSE, false);
    testFail(Spec.FALSE, true);
  }

  @Test
  public void testBeNull() {
    test(Spec.NULL, null);
    testFail(Spec.NULL, "string");
  }

  @Test
  public void testRaise() {
    test(Spec.raise(IllegalArgumentException.class), new IllegalArgumentException());
    test(Spec.raise(RuntimeException.class), new IllegalArgumentException());

    testFail(Spec.raise(IllegalArgumentException.class), null);
    testFail(Spec.raise(IllegalArgumentException.class), new RuntimeException());
  }

  @Test
  public void testSucceed() {
    test(Spec.succeed(), null);

    testFail(Spec.succeed(), new RuntimeException());
    testFail(Spec.succeed(), new IllegalArgumentException());
    testFail(Spec.succeed(), new IllegalArgumentException());
  }

  @Test
  public void testMatch() {
    test(Spec.match(is(nullValue())), null);
    testFail(Spec.match(is(nullValue())), "");
  }

  @Test
  public void testGreatherThan() {
    test(Spec.greatherThan(10), 11);
    testFail(Spec.greatherThan(10), 10);
    testFail(Spec.greatherThan(10), 9);
  }

  @Test
  public void testGreatherThanOrEqual() {
    test(Spec.greatherThanOrEqual(10), 11);
    test(Spec.greatherThanOrEqual(10), 10);
    testFail(Spec.greatherThanOrEqual(10), 9);
  }

  @Test
  public void testLessThan() {
    test(Spec.lessThan(10), 9);
    testFail(Spec.lessThan(10), 10);
    testFail(Spec.lessThan(10), 11);
  }

  @Test
  public void testLessThanOrEqual() {
    test(Spec.lessThanOrEqual(10), 9);
    test(Spec.lessThanOrEqual(10), 10);
    testFail(Spec.lessThanOrEqual(10), 11);
  }

  @Test
  public void testHelpers() {
    assertSame(alwaysTrue, Spec.should(alwaysTrue));
    assertEquals("some reason", Spec.because("some reason"));
    assertEquals("description", Spec.otherwise("description"));
  }

}
