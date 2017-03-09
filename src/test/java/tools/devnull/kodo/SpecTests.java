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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tools.devnull.kodo.Expectation.because;
import static tools.devnull.kodo.Expectation.it;

/**
 * Tests for the SpecDefinition implementation
 *
 * @author ataxexe
 */
@RunWith(MockitoJUnitRunner.class)
public class SpecTests {

  private Object target = new Object();
  private SpecDefinition<Object> spec = Spec.given(target);
  private String message = "a message";
  private Consumer throwAssertionError = because(message);
  private Object value = new Object();
  @Mock
  private Consumer operation;
  @Mock
  private Runnable runnableOperation;
  @Mock
  private Consumer failOperation;
  private RuntimeException exception = new RuntimeException();
  @Mock
  private Predicate test;
  @Mock
  private Predicate failTest;
  @Mock
  private Function function;
  @Mock
  private Function<Object, Boolean> booleanFunction;

  @Before
  public void initialize() {
    when(test.test(any())).thenReturn(true);
    when(failTest.test(any())).thenReturn(false);
    when(function.apply(target)).thenReturn(value);
    doThrow(exception).when(failOperation).accept(any());
    when(booleanFunction.apply(target)).thenReturn(true);
  }

  @Test
  public void testReturns() {
    assertSame(spec, spec.expect(function, test));
    assertSame(spec, spec.expect(function, test, throwAssertionError));

    assertSame(spec, spec.expect(operation, test));
    assertSame(spec, spec.expect(operation, test, throwAssertionError));

    assertSame(spec, spec.expect(booleanFunction));
    assertSame(spec, spec.expect(booleanFunction, throwAssertionError));

    assertSame(spec, spec.expect(true));
    assertSame(spec, spec.expect(true, throwAssertionError));

    assertSame(spec, spec.when(operation));
    assertSame(spec, spec.when(runnableOperation));

    assertSame(spec, spec.each(Object.class, t -> Collections.emptyList(), s -> {}));

    assertNotSame(spec, spec.given(new Object()));
    assertNotSame(spec, spec.given(t -> new Object()));

    assertNotSame(spec, spec.begin());

    assertNotSame(spec, spec.onFail(throwAssertionError));
  }

  @Test
  public void testBegin() {
    assertNotNull(Spec.begin());
    assertNotNull(Spec.describe(""));
    assertNotNull(Spec.given(new Object()));
  }

  @Test
  public void testGiven() {
    Function f = mock(Function.class);
    Spec.given(target).given(f);
    verify(f).apply(target);
  }

  @Test
  public void testWhen() {
    spec.when(operation);
    verify(operation).accept(target);
  }

  @Test
  public void testWhenWithRunnable() {
    spec.when(runnableOperation);
    verify(runnableOperation).run();
  }

  @Test
  public void testExpectWithFunction() {
    spec.expect(function, test);
    verify(function).apply(target);
    verify(test).test(value);
  }

  @Test
  public void testExpectWithConsumer() {
    spec.expect(operation, test);
    verify(operation).accept(target);

    spec.expect(failOperation, test);
    verify(test).test(exception);
  }

  @Test
  public void testExpectWithBoolean() {
    spec.expect(booleanFunction);

    verify(booleanFunction).apply(target);
  }

  @Test(expected = AssertionError.class)
  public void testFailExpectWithBoolean() {
    spec.expect(false);
  }

  @Test(expected = AssertionError.class)
  public void testFailExpectWithBooleanFunction() {
    spec.expect(o -> false);
  }

  @Test
  public void testSucceedExpectWithBoolean() {
    spec.expect(true);
  }

  @Test
  public void testSucceedExpectWithBooleanFunction() {
    spec.expect(o -> true);
  }

  @Test
  public void testEach() {
    List<Integer> ints = Arrays.asList(1, 2, 3);
    Predicate<Integer> predicate = mock(Predicate.class);
    when(predicate.test(anyInt())).thenReturn(true);

    Spec.given(ints)
        .each(Integer.class, i -> i.expect(it(), predicate));

    verify(predicate).test(1);
    verify(predicate).test(2);
    verify(predicate).test(3);
  }

  @Test
  public void testMessages() {
    assertMessage(() -> spec.expect(function, failTest, throwAssertionError));
    assertMessage(() -> spec.expect(operation, failTest, throwAssertionError));

    when(booleanFunction.apply(target)).thenReturn(false);
    assertMessage(() -> spec.expect(booleanFunction, throwAssertionError));
    assertMessage(() -> spec.expect(false, throwAssertionError));
  }

  @Test
  public void testDefaultMessage() {
    try {
      spec.expect(o -> "some value", failTest);
      throw new Error();
    } catch (AssertionError error) {
      assertEquals("for value: some value", error.getMessage());
    }
  }

  @Test
  public void testDefaultMessageWithNullTarget() {
    try {
      spec.expect(o -> null, failTest);
      throw new Error();
    } catch (AssertionError error) {
      assertEquals("for value: null", error.getMessage());
    }
  }

  @Test
  public void testFailExpectation() {
    Consumer consumer = mock(Consumer.class);
    try {
      spec.expect(o -> null, failTest, consumer);
      throw new Error();
    } catch (AssertionError error) {
      verify(consumer).accept(error);
    }
  }

  private void assertMessage(Runnable command) {
    try {
      command.run();
      throw new Error();
    } catch (AssertionError error) {
      assertEquals(message, error.getMessage());
    }
  }

  @Test
  public void testAttributes() {
    String description = "";
    Object newTarget = new Object();

    DefaultSpecDefinition s = new DefaultSpecDefinition(description, target, throwAssertionError);
    assertSame(s.target, target);
    assertSame(s.description, description);
    assertSame(s.defaultFailOperation, throwAssertionError);

    DefaultSpecDefinition s1 = (DefaultSpecDefinition) s.given(newTarget);
    assertSame(s1.target, newTarget);
    assertSame(s1.description, description);
    assertSame(s1.defaultFailOperation, throwAssertionError);

    s1 = (DefaultSpecDefinition) s.given(o ->  newTarget);
    assertSame(s1.target, newTarget);
    assertSame(s1.description, description);
    assertSame(s1.defaultFailOperation, throwAssertionError);
  }

}
