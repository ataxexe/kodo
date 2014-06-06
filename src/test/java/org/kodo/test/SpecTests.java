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
import org.kodo.Spec;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;

/**
 * @author Marcelo Guimarães
 */
public class SpecTests {

  private void test(Consumer consumer, Object value) {
    consumer.accept(value);
  }

  private void testFail(Consumer consumer, Object value) {
    try {
      test(consumer, value);
      throw new AssertionError("No exception thrown");
    } catch (AssertionError error) {
      // ok
    }
  }

  @Test
  public void testShouldBeWithObject() {
    test(Spec.be("test"), "test");
    testFail(Spec.be("test"), "");

    test(Spec.be(1), 1);
    testFail(Spec.be(1), 2);
  }

  @Test
  public void testShouldNotBeWithObject() {
    testFail(Spec.notBe("test"), "test");
    test(Spec.notBe("test"), "");

    testFail(Spec.notBe(1), 1);
    test(Spec.notBe(1), 2);
  }

  @Test
  public void testShouldEqual() {
    test(Spec.equal("test"), "test");
    testFail(Spec.equal("test"), "");

    test(Spec.equal(1), 1);
    testFail(Spec.equal(1), 2);
  }

  @Test
  public void testShouldNotEqual() {
    testFail(Spec.notEqual("test"), "test");
    test(Spec.notEqual("test"), "");

    testFail(Spec.notEqual(1), 1);
    test(Spec.notEqual(1), 2);
  }

  @Test
  public void testShouldBeWithPredicate() {
    test(Spec.be(value -> value.toString().length() > 5), "123456");
    test(Spec.be(value -> value.toString().length() > 5), 123456);
    test(Spec.be(value -> true), null);
    test(Spec.be(value -> true), "");
    test(Spec.be(value -> true), 1);

    testFail(Spec.be(value -> value.toString().length() > 5), "12345");
    testFail(Spec.be(value -> value.toString().length() > 5), 12345);
    testFail(Spec.be(value -> false), null);
    testFail(Spec.be(value -> false), "");
    testFail(Spec.be(value -> false), 1);
  }

  @Test
  public void testShouldNotBeWithPredicate() {
    testFail(Spec.notBe(value -> value.toString().length() > 5), "123456");
    testFail(Spec.notBe(value -> value.toString().length() > 5), 123456);
    testFail(Spec.notBe(value -> true), null);
    testFail(Spec.notBe(value -> true), "");
    testFail(Spec.notBe(value -> true), 1);

    test(Spec.notBe(value -> value.toString().length() > 5), "12345");
    test(Spec.notBe(value -> value.toString().length() > 5), 12345);
    test(Spec.notBe(value -> false), null);
    test(Spec.notBe(value -> false), "");
    test(Spec.notBe(value -> false), 1);
  }

  @Test
  public void testShouldBeEmpty() {
    test(Spec.EMPTY, Collections.emptyList());
    test(Spec.EMPTY, Collections.emptySet());

    testFail(Spec.EMPTY, Arrays.asList(1, 2, 3));
    testFail(Spec.EMPTY, new HashSet(Arrays.asList(1, 2, 3)));
  }

  @Test
  public void testShouldBeTrue() {
    test(Spec.TRUE, true);
    testFail(Spec.TRUE, false);
  }

  @Test
  public void testShouldBeFalse() {
    test(Spec.FALSE, false);
    testFail(Spec.FALSE, true);
  }

  @Test
  public void testShouldBeNull() {
    test(Spec.NULL, null);
    testFail(Spec.NULL, "string");
  }

  @Test
  public void testShouldRaise() {
    test(Spec.raise(IllegalArgumentException.class), new IllegalArgumentException());
    test(Spec.raise(RuntimeException.class), new IllegalArgumentException());

    testFail(Spec.raise(IllegalArgumentException.class), null);
    testFail(Spec.raise(IllegalArgumentException.class), new RuntimeException());
  }

  @Test
  public void testShouldSucceed() {
    test(Spec.succeed(), null);

    testFail(Spec.succeed(), new RuntimeException());
    testFail(Spec.succeed(), new IllegalArgumentException());
    testFail(Spec.succeed(), new IllegalArgumentException());
  }

  @Test
  public void testShouldMatch() {
    test(Spec.match(is(nullValue())), null);
    testFail(Spec.match(is(nullValue())), "");
  }

}
