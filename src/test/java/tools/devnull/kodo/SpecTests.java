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
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Marcelo Guimarães
 */
@RunWith(MockitoJUnitRunner.class)
public class SpecTests {

  private Object target = new Object();
  private Scenario<Object> scenario = Spec.given(target);
  private String message = "a message";
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

  @Before
  public void initialize() {
    when(test.test(anyObject())).thenReturn(true);
    when(failTest.test(anyObject())).thenReturn(false);
    when(function.apply(target)).thenReturn(value);
    doThrow(exception).when(failOperation).accept(anyObject());
  }

  @Test
  public void testReturns() {

    assertSame(scenario, scenario.expect(function, test));
    assertSame(scenario, scenario.expect(function, test, message));

    assertSame(scenario, scenario.expect(operation, test));
    assertSame(scenario, scenario.expect(operation, test, message));

    assertSame(scenario, scenario.when(operation));
    assertSame(scenario, scenario.when(runnableOperation));
  }

  @Test
  public void testWhen() {
    scenario.when(operation);
    verify(operation).accept(target);
  }

  @Test
  public void testWhenWithRunnable() {
    scenario.when(runnableOperation);
    verify(runnableOperation).run();
  }

  @Test
  public void testThenWithFunction() {
    scenario.expect(function, test);
    verify(function).apply(target);
    verify(test).test(value);
  }

  @Test
  public void testThenWithConsumer() {
    scenario.expect(operation, test);
    verify(operation).accept(target);

    scenario.expect(failOperation, test);
    verify(test).test(exception);
  }

  @Test
  public void testMessages() {
    assertMessage(() -> scenario.expect(function, failTest, message));
    assertMessage(() -> scenario.expect(operation, failTest, message));
  }

  @Test
  public void testHelpers() {
    Object o = new Object();
    assertSame(o, Expectation.value(o).apply(null));
    assertSame(o, Expectation.it().apply(o));
  }

  @Test
  public void testDefaultMessage() {
    try {
      scenario.expect(o -> "some value", failTest);
      throw new Error();
    } catch (AssertionError error) {
      assertEquals("for value: some value", error.getMessage());
    }
  }

  @Test
  public void testDefaultMessageWithNullTarget() {
    try {
      scenario.expect(o -> null, failTest);
      throw new Error();
    } catch (AssertionError error) {
      assertEquals("for value: null", error.getMessage());
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

}
