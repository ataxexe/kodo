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

import java.util.function.Predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.kodo.ComparablePredicates.*;

/**
 * @author Marcelo Guimarães
 */
public class ComparablePredicatesTest {

  private void test(Predicate predicate, Object value) {
    assertTrue(predicate.test(value));
  }

  private void testFail(Predicate predicate, Object value) {
    assertFalse(predicate.test(value));
  }

  @Test
  public void testEqual() {
    test(equal(10), 10);
    testFail(equal(10), 11);
    testFail(equal(10), 9);
  }

  @Test
  public void testGreatherThan() {
    test(greatherThan(10), 11);
    testFail(greatherThan(10), 10);
    testFail(greatherThan(10), 9);
  }

  @Test
  public void testGreatherThanOrEqual() {
    test(greatherThanOrEqual(10), 11);
    test(greatherThanOrEqual(10), 10);
    testFail(greatherThanOrEqual(10), 9);
  }

  @Test
  public void testLessThan() {
    test(lessThan(10), 9);
    testFail(lessThan(10), 10);
    testFail(lessThan(10), 11);
  }

  @Test
  public void testLessThanOrEqual() {
    test(lessThanOrEqual(10), 9);
    test(lessThanOrEqual(10), 10);
    testFail(lessThanOrEqual(10), 11);
  }

}
