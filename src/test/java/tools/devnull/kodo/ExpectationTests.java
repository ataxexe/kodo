/*
 * The MIT License
 *
 * Copyright (c) 2014 Marcelo Guimaraes <ataxexe@backpackcloud.com>
 *
 * Permission  is hereby granted, free of charge, to any person obtaining
 * a  copy  of  this  software  and  associated  documentation files (the
 * "Software"),  to  deal  in the Software without restriction, including
 * without  limitation  the  rights to use, copy, modify, merge, publish,
 * distribute,  sublicense,  and/or  sell  copies of the Software, and to
 * permit  persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * The  above  copyright  notice  and  this  permission  notice  shall be
 * included  in  all  copies  or  substantial  portions  of the Software.
 *
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

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static junit.framework.TestCase.assertSame;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tools.devnull.kodo.Expectation.it;
import static tools.devnull.kodo.Expectation.otherwise;
import static tools.devnull.kodo.Expectation.the;
import static tools.devnull.kodo.Expectation.to;

/**
 * The test suite for {@link Expectation}
 */
public class ExpectationTests {

  private Predicate noMoreThan5Chars =
      value -> value.toString().length() > 5;
  private Predicate alwaysTrue = value -> true;
  private Predicate alwaysFalse = value -> false;
  private Object value = new Object();

  @Test
  public void testBeWithObject() {
    assertTrue(to().be("test").test("test"));
    assertFalse(to().be("test").test(""));

    assertTrue(to().be(1).test(1));
    assertFalse(to().be(1).test(2));

    assertTrue(to().be("test").test("test"));
    assertFalse(to().be("test").test(""));

    assertTrue(to().be(1).test(1));
    assertFalse(to().be(1).test(2));
  }

  @Test
  public void testBeNull() {
    assertTrue(to().beNull().test(null));
    assertTrue(to().beNull(Object.class).test(null));
    assertFalse(to().beNull().test(new Object()));
    assertFalse(to().beNull(Object.class).test(new Object()));
  }

  @Test
  public void testEqual() {
    assertTrue(to().eq("test").test("test"));
    assertFalse(to().eq("test").test(""));

    assertTrue(to().eq(1).test(1));
    assertFalse(to().eq(1).test(2));
  }

  @Test
  public void testBeWithPredicate() {
    assertTrue(to().be(noMoreThan5Chars).test("123456"));
    assertTrue(to().be(noMoreThan5Chars).test(123456));
    assertTrue(to().be(alwaysTrue).test(value));
    assertTrue(to().be(alwaysTrue).test(""));
    assertTrue(to().be(alwaysTrue).test(1));

    assertFalse(to().be(noMoreThan5Chars).test("12345"));
    assertFalse(to().be(noMoreThan5Chars).test(12345));
    assertFalse(to().be(alwaysFalse).test(value));
    assertFalse(to().be(alwaysFalse).test(""));
    assertFalse(to().be(alwaysFalse).test(1));
  }

  @Test
  public void testHaveWithPredicate() {
    assertTrue(to().have(noMoreThan5Chars).test("123456"));
    assertTrue(to().have(noMoreThan5Chars).test(123456));
    assertTrue(to().have(alwaysTrue).test(value));
    assertTrue(to().have(alwaysTrue).test(""));
    assertTrue(to().have(alwaysTrue).test(1));

    assertFalse(to().have(noMoreThan5Chars).test("12345"));
    assertFalse(to().have(noMoreThan5Chars).test(12345));
    assertFalse(to().have(alwaysFalse).test(value));
    assertFalse(to().have(alwaysFalse).test(""));
    assertFalse(to().have(alwaysFalse).test(1));
  }

  @Test
  public void testRaise() {
    assertTrue(to().raise(IllegalArgumentException.class).test(new IllegalArgumentException()));
    assertTrue(to().raise(RuntimeException.class).test(new IllegalArgumentException()));

    assertFalse(to().raise(IllegalArgumentException.class).test(null));
    assertFalse(to().raise(IllegalArgumentException.class).test(new RuntimeException()));
  }

  @Test
  public void testSucceed() {
    assertTrue(to().succeed().test(null));

    assertFalse(to().succeed().test(new RuntimeException()));
    assertFalse(to().succeed().test(new IllegalArgumentException()));
    assertFalse(to().succeed().test(new IllegalArgumentException()));
  }

  @Test
  public void testFail() {
    assertFalse(to().fail().test(null));

    assertTrue(to().fail().test(new RuntimeException()));
    assertTrue(to().fail().test(new IllegalArgumentException()));
    assertTrue(to().fail().test(new IllegalArgumentException()));
  }

  @Test
  public void testMatch() {
    assertTrue(to().match(is(nullValue())).test(null));
    assertFalse(to().match(is(nullValue())).test(""));
  }

  @Test
  public void testNegate() {
    Predicate predicate = mock(Predicate.class);
    Predicate negate = mock(Predicate.class);

    when(negate.test(value)).thenReturn(true);
    when(predicate.negate()).thenReturn(negate);

    assertTrue(to().not().be(predicate).test(value));
    assertTrue(to().not(predicate).test(value));

    verify(predicate, times(2)).negate();
    verify(negate, times(2)).test(value);
  }

  @Test
  public void testHelpers() {
    assertSame(alwaysTrue, to(alwaysTrue));
    assertSame(alwaysFalse, to(alwaysFalse));

    Object o = new Object();

    assertSame(o, the(o).get());
    assertSame(o, it().apply(o));

    Consumer consumer = mock(Consumer.class);
    assertSame(consumer, otherwise(consumer));
  }

  @Test
  public void testExec() {
    Function function = mock(Function.class);
    when(function.apply(value)).thenReturn(value);

    Expectation.doing(function).accept(value);

    verify(function).apply(value);
  }

  @Test
  public void testFollow() {
    Consumer definition = mock(Consumer.class);

    to().follow(definition).test(value);
    Spec.given(value)
        .expect(it(), to().follow(definition));

    verify(definition, times(2)).accept(any(SpecDefinition.class));

    Spec.given(value)
        .expect(it(), to().follow(spec -> spec.
            expect(it(), to().be(value))));
  }

}
