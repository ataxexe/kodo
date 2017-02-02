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

import java.util.function.Function;
import java.util.function.Predicate;

import static junit.framework.TestCase.assertSame;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tools.devnull.kodo.Spec.exec;
import static tools.devnull.kodo.Spec.should;
import static tools.devnull.kodo.Spec.to;

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
    assertTrue(should().be("test").test("test"));
    assertFalse(should().be("test").test(""));

    assertTrue(should().be(1).test(1));
    assertFalse(should().be(1).test(2));

    assertTrue(to().be("test").test("test"));
    assertFalse(to().be("test").test(""));

    assertTrue(to().be(1).test(1));
    assertFalse(to().be(1).test(2));

    assertTrue(to().be(null).test(null));
    assertFalse(to().be(null).test(new Object()));
  }

  @Test
  public void testEqual() {
    assertTrue(should().equal("test").test("test"));
    assertFalse(should().equal("test").test(""));

    assertTrue(should().equal(1).test(1));
    assertFalse(should().equal(1).test(2));
  }

  @Test
  public void testBeWithPredicate() {
    assertTrue(should().be(noMoreThan5Chars).test("123456"));
    assertTrue(should().be(noMoreThan5Chars).test(123456));
    assertTrue(should().be(alwaysTrue).test(value));
    assertTrue(should().be(alwaysTrue).test(""));
    assertTrue(should().be(alwaysTrue).test(1));

    assertFalse(should().be(noMoreThan5Chars).test("12345"));
    assertFalse(should().be(noMoreThan5Chars).test(12345));
    assertFalse(should().be(alwaysFalse).test(value));
    assertFalse(should().be(alwaysFalse).test(""));
    assertFalse(should().be(alwaysFalse).test(1));
  }

  @Test
  public void testHaveWithPredicate() {
    assertTrue(should().have(noMoreThan5Chars).test("123456"));
    assertTrue(should().have(noMoreThan5Chars).test(123456));
    assertTrue(should().have(alwaysTrue).test(value));
    assertTrue(should().have(alwaysTrue).test(""));
    assertTrue(should().have(alwaysTrue).test(1));

    assertFalse(should().have(noMoreThan5Chars).test("12345"));
    assertFalse(should().have(noMoreThan5Chars).test(12345));
    assertFalse(should().have(alwaysFalse).test(value));
    assertFalse(should().have(alwaysFalse).test(""));
    assertFalse(should().have(alwaysFalse).test(1));
  }

  @Test
  public void testRaise() {
    assertTrue(should().raise(IllegalArgumentException.class).test(new IllegalArgumentException()));
    assertTrue(should().raise(RuntimeException.class).test(new IllegalArgumentException()));

    assertFalse(should().raise(IllegalArgumentException.class).test(null));
    assertFalse(should().raise(IllegalArgumentException.class).test(new RuntimeException()));
  }

  @Test
  public void testSucceed() {
    assertTrue(should().succeed().test(null));

    assertFalse(should().succeed().test(new RuntimeException()));
    assertFalse(should().succeed().test(new IllegalArgumentException()));
    assertFalse(should().succeed().test(new IllegalArgumentException()));
  }

  @Test
  public void testFail2() {
    assertFalse(should().fail().test(null));

    assertTrue(should().fail().test(new RuntimeException()));
    assertTrue(should().fail().test(new IllegalArgumentException()));
    assertTrue(should().fail().test(new IllegalArgumentException()));
  }

  @Test
  public void testMatch() {
    assertTrue(should().match(is(nullValue())).test(null));
    assertFalse(should().match(is(nullValue())).test(""));
  }

  @Test
  public void testNegate() {
    Predicate predicate = mock(Predicate.class);
    Predicate negate = mock(Predicate.class);

    when(negate.test(value)).thenReturn(true);
    when(predicate.negate()).thenReturn(negate);

    assertTrue(should().not().be(predicate).test(value));
    assertTrue(should().not(predicate).test(value));

    verify(predicate, times(2)).negate();
    verify(negate, times(2)).test(value);
  }

  @Test
  public void testHelpers() {
    assertSame(alwaysTrue, should(alwaysTrue));
    assertSame(alwaysFalse, to(alwaysFalse));
  }

  @Test
  public void testExec() {
    Function function = mock(Function.class);
    when(function.apply(value)).thenReturn(value);

    exec(function).accept(value);

    verify(function).apply(value);
  }

}
