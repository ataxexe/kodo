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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kodo.Scenario;
import org.kodo.TestScenario;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.intThat;
import static org.mockito.Mockito.*;

/**
 * @author Marcelo Guimarães
 */
@RunWith(MockitoJUnitRunner.class)
public class ScenarioTests {

  private Object target = new Object();
  private Scenario<Object> scenario = TestScenario.given(target);
  private Scenario<Object> listScenario = TestScenario.given(Arrays.asList(1, 2, 3));
  private String message = "a message";
  private Object value = new Object();
  @Mock
  private Consumer operation;
  @Mock
  private Consumer failOperation;
  private RuntimeException exception = new RuntimeException();
  @Mock
  private Predicate test;
  @Mock
  private Predicate failTest;
  @Mock
  private Function function;

  private Matcher<Integer> isBetween1and3 = new BaseMatcher<Integer>() {
    @Override
    public boolean matches(Object o) {
      Integer number = (Integer) o;
      return number >= 1 && number <= 3;
    }

    @Override
    public void describeTo(Description description) {
      description.appendText("<number between 1 and 3>");
    }
  };

  @Before
  public void initialize() {
    when(test.test(anyObject())).thenReturn(true);
    when(failTest.test(anyObject())).thenReturn(false);
    when(function.apply(target)).thenReturn(value);
    doThrow(exception).when(failOperation).accept(anyObject());
  }

  @Test
  public void testReturns() {
    assertSame(scenario, scenario.and(operation, test));
    assertSame(scenario, scenario.and(operation, test, message));

    assertSame(scenario, scenario.and(test));
    assertSame(scenario, scenario.and(test, message));

    assertSame(listScenario, listScenario.each(test));
    assertSame(listScenario, listScenario.each(test, message));

    assertSame(scenario, scenario.it(test));
    assertSame(scenario, scenario.it(test, message));

    assertSame(scenario, scenario.the(function, test));
    assertSame(scenario, scenario.the(function, test, message));

    assertSame(scenario, scenario.the(value, test));
    assertSame(scenario, scenario.the(value, test, message));

    assertSame(scenario, scenario.then(operation, test));
    assertSame(scenario, scenario.then(operation, test, message));

    assertSame(scenario, scenario.then(value, test));
    assertSame(scenario, scenario.then(value, test, message));

    assertSame(scenario, scenario.and(value, test));
    assertSame(scenario, scenario.and(value, test, message));

    assertSame(scenario, scenario.thenIt(test));
    assertSame(scenario, scenario.thenIt(test, message));

    assertSame(scenario, scenario.when(operation));
  }

  @Test
  public void testWhen() {
    scenario.when(operation);
    verify(operation).accept(target);
  }

  @Test
  public void testAndWithConsumerAndPredicate() {
    scenario.and(operation, test);

    verify(operation).accept(target);
    verify(test).test(null);
  }

  @Test
  public void testAndWithConsumerAndPredicateForException() {
    scenario.and(failOperation, test);

    verify(failOperation).accept(target);
    verify(test).test(exception);
  }

  @Test
  public void testAndWithPredicate() {
    scenario.and(test);

    verify(test).test(target);
  }

  @Test
  public void testEach() {
    listScenario.each(test);
    verify(test, times(3)).test(intThat(isBetween1and3));
  }

  @Test
  public void testIt() {
    scenario.it(test);
    verify(test).test(target);
  }

  @Test
  public void testTheWithFunction() {
    scenario.the(function, test);
    verify(function).apply(target);
    verify(test).test(value);
  }

  @Test
  public void testTheWithValue() {
    scenario.the(value, test);
    verify(test).test(value);
  }

  @Test
  public void testThenWithConsumer() {
    scenario.then(operation, test);
    verify(operation).accept(target);

    scenario.then(failOperation, test);
    verify(test).test(exception);
  }

  @Test
  public void testThenWithValue() {
    scenario.then(value, test);
    verify(test).test(value);
  }

  @Test
  public void testAndWithValue() {
    scenario.and(value, test);
    verify(test).test(value);
  }

  @Test
  public void testThenIt() {
    scenario.thenIt(test);
    verify(test).test(target);
  }

  @Test
  public void testMessages() {
    assertMessage(() -> scenario.and(operation, failTest, message));
    assertMessage(() -> scenario.and(failTest, message));
    assertMessage(() -> listScenario.each(failTest, message));
    assertMessage(() -> scenario.it(failTest, message));
    assertMessage(() -> scenario.the(function, failTest, message));
    assertMessage(() -> scenario.the(value, failTest, message));
    assertMessage(() -> scenario.then(operation, failTest, message));
    assertMessage(() -> scenario.then(value, failTest, message));
    assertMessage(() -> scenario.and(value, failTest, message));
    assertMessage(() -> scenario.thenIt(failTest, message));
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
