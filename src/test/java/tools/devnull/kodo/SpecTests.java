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

import static junit.framework.TestCase.assertSame;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tools.devnull.kodo.Spec.should;
import static tools.devnull.kodo.Spec.to;
import static tools.devnull.kodo.TestHelper.test;
import static tools.devnull.kodo.TestHelper.testFail;

/**
 * The test suite for {@link Spec}
 */
public class SpecTests {

  private Predicate noMoreThan5Chars =
      value -> value.toString().length() > 5;
  private Predicate alwaysTrue = value -> true;
  private Predicate alwaysFalse = value -> false;
  private Object value = new Object();

  @Test
  public void testBeWithObject() {
    test(should().be("test"), "test");
    testFail(should().be("test"), "");

    test(should().be(1), 1);
    testFail(should().be(1), 2);

    test(to().be("test"), "test");
    testFail(to().be("test"), "");

    test(to().be(1), 1);
    testFail(to().be(1), 2);
  }

  @Test
  public void testEqual() {
    test(should().equal("test"), "test");
    testFail(should().equal("test"), "");

    test(should().equal(1), 1);
    testFail(should().equal(1), 2);
  }

  @Test
  public void testBeWithPredicate() {
    test(should().be(noMoreThan5Chars), "123456");
    test(should().be(noMoreThan5Chars), 123456);
    test(should().be(alwaysTrue), value);
    test(should().be(alwaysTrue), "");
    test(should().be(alwaysTrue), 1);

    testFail(should().be(noMoreThan5Chars), "12345");
    testFail(should().be(noMoreThan5Chars), 12345);
    testFail(should().be(alwaysFalse), value);
    testFail(should().be(alwaysFalse), "");
    testFail(should().be(alwaysFalse), 1);
  }

  @Test
  public void testHaveWithPredicate() {
    test(should().have(noMoreThan5Chars), "123456");
    test(should().have(noMoreThan5Chars), 123456);
    test(should().have(alwaysTrue), value);
    test(should().have(alwaysTrue), "");
    test(should().have(alwaysTrue), 1);

    testFail(should().have(noMoreThan5Chars), "12345");
    testFail(should().have(noMoreThan5Chars), 12345);
    testFail(should().have(alwaysFalse), value);
    testFail(should().have(alwaysFalse), "");
    testFail(should().have(alwaysFalse), 1);
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

  @Test
  public void testNegate() {
    Predicate predicate = mock(Predicate.class);
    Predicate negate = mock(Predicate.class);

    when(negate.test(value)).thenReturn(true);
    when(predicate.negate()).thenReturn(negate);

    test(should().not().be(predicate), value);
    verify(predicate).negate();
    verify(negate).test(value);
  }

  @Test
  public void testHelpers() {
    assertSame(alwaysTrue, should(alwaysTrue));
    assertSame(alwaysFalse, to(alwaysFalse));
  }

}
