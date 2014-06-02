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
import org.kodo.Should;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;

/**
 * @author Marcelo Guimarães
 */
public class ShouldTests {

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
    test(Should.be("test"), "test");
    testFail(Should.be("test"), "");

    test(Should.be(1), 1);
    testFail(Should.be(1), 2);
  }

  @Test
  public void testShouldNotBeWithObject() {
    testFail(Should.notBe("test"), "test");
    test(Should.notBe("test"), "");

    testFail(Should.notBe(1), 1);
    test(Should.notBe(1), 2);
  }

  @Test
  public void testShouldEqual() {
    test(Should.equal("test"), "test");
    testFail(Should.equal("test"), "");

    test(Should.equal(1), 1);
    testFail(Should.equal(1), 2);
  }

  @Test
  public void testShouldNotEqual() {
    testFail(Should.notEqual("test"), "test");
    test(Should.notEqual("test"), "");

    testFail(Should.notEqual(1), 1);
    test(Should.notEqual(1), 2);
  }

  @Test
  public void testShouldBeWithPredicate() {
    test(Should.be(value -> value.toString().length() > 5), "123456");
    test(Should.be(value -> value.toString().length() > 5), 123456);
    test(Should.be(value -> true), null);
    test(Should.be(value -> true), "");
    test(Should.be(value -> true), 1);

    testFail(Should.be(value -> value.toString().length() > 5), "12345");
    testFail(Should.be(value -> value.toString().length() > 5), 12345);
    testFail(Should.be(value -> false), null);
    testFail(Should.be(value -> false), "");
    testFail(Should.be(value -> false), 1);
  }

  @Test
  public void testShouldNotBeWithPredicate() {
    testFail(Should.notBe(value -> value.toString().length() > 5), "123456");
    testFail(Should.notBe(value -> value.toString().length() > 5), 123456);
    testFail(Should.notBe(value -> true), null);
    testFail(Should.notBe(value -> true), "");
    testFail(Should.notBe(value -> true), 1);

    test(Should.notBe(value -> value.toString().length() > 5), "12345");
    test(Should.notBe(value -> value.toString().length() > 5), 12345);
    test(Should.notBe(value -> false), null);
    test(Should.notBe(value -> false), "");
    test(Should.notBe(value -> false), 1);
  }

  @Test
  public void testShouldBeEmpty() {
    test(Should.BE_EMPTY, Collections.emptyList());
    test(Should.BE_EMPTY, Collections.emptySet());
    test(Should.beEmpty(), Collections.emptyList());
    test(Should.beEmpty(), Collections.emptySet());

    testFail(Should.BE_EMPTY, Arrays.asList(1, 2, 3));
    testFail(Should.BE_EMPTY, new HashSet(Arrays.asList(1, 2, 3)));
    testFail(Should.beEmpty(), Arrays.asList(1, 2, 3));
    testFail(Should.beEmpty(), new HashSet(Arrays.asList(1, 2, 3)));
  }

  @Test
  public void testShouldNotBeEmpty() {
    test(Should.NOT_BE_EMPTY, Arrays.asList(1, 2, 3));
    test(Should.NOT_BE_EMPTY, new HashSet(Arrays.asList(1, 2, 3)));
    test(Should.notBeEmpty(), Arrays.asList(1, 2, 3));
    test(Should.notBeEmpty(), new HashSet(Arrays.asList(1, 2, 3)));

    testFail(Should.notBeEmpty(), Collections.emptyList());
    testFail(Should.notBeEmpty(), Collections.emptySet());
  }

  @Test
  public void testShouldBeTrue() {
    test(Should.BE_TRUE, true);
    test(Should.beTrue(), true);

    testFail(Should.BE_TRUE, false);
    testFail(Should.beTrue(), false);
  }

  @Test
  public void testShouldBeFalse() {
    test(Should.BE_FALSE, false);
    test(Should.beFalse(), false);

    testFail(Should.BE_FALSE, true);
    testFail(Should.beFalse(), true);
  }

  @Test
  public void testShouldBeNull() {
    test(Should.BE_NULL, null);
    test(Should.beNull(), null);

    testFail(Should.BE_NULL, "string");
    testFail(Should.beNull(), "string");
  }

  @Test
  public void testShouldNotBeNull() {
    test(Should.NOT_BE_NULL, "string");
    test(Should.notBeNull(), "string");

    testFail(Should.NOT_BE_NULL, null);
    testFail(Should.notBeNull(), null);
  }

  @Test
  public void testShouldRaise() {
    test(Should.raise(IllegalArgumentException.class), new IllegalArgumentException());
    test(Should.raise(RuntimeException.class), new IllegalArgumentException());

    testFail(Should.raise(IllegalArgumentException.class), null);
    testFail(Should.raise(IllegalArgumentException.class), new RuntimeException());
  }

  @Test
  public void testShouldSucceed() {
    test(Should.succeed(), null);

    testFail(Should.succeed(), new RuntimeException());
    testFail(Should.succeed(), new IllegalArgumentException());
    testFail(Should.succeed(), new IllegalArgumentException());
  }

  @Test
  public void testShouldMatch() {
    test(Should.match(is(nullValue())), null);
    testFail(Should.match(is(nullValue())), "");
  }

}
