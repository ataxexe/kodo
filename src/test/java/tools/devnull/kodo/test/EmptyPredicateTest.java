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
import tools.devnull.kodo.Spec;

import java.util.Collection;
import java.util.Map;

import static tools.devnull.kodo.test.SpecTests.test;
import static tools.devnull.kodo.test.SpecTests.testFail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Marcelo Guimarães
 */
public class EmptyPredicateTest {

  private void testPredicate(Object target) {
    test(Spec.EMPTY, target);
  }

  private void testFailPredicate(Object target) {
    testFail(Spec.EMPTY, target);
  }

  @Test
  public void testBooleanArrayPredicate() {
    boolean[] emptyArray = new boolean[0];
    boolean[] notEmptyArray = new boolean[10];
    testPredicate(emptyArray);
    testFailPredicate(notEmptyArray);
  }

  @Test
  public void testByteArrayPredicate() {
    byte[] emptyArray = new byte[0];
    byte[] notEmptyArray = new byte[10];
    testPredicate(emptyArray);
    testFailPredicate(notEmptyArray);
  }

  @Test
  public void testCharArrayPredicate() {
    char[] emptyArray = new char[0];
    char[] notEmptyArray = new char[10];
    testPredicate(emptyArray);
    testFailPredicate(notEmptyArray);
  }

  @Test
  public void testDoubleArrayPredicate() {
    double[] emptyArray = new double[0];
    double[] notEmptyArray = new double[10];
    testPredicate(emptyArray);
    testFailPredicate(notEmptyArray);
  }

  @Test
  public void testFloatArrayPredicate() {
    float[] emptyArray = new float[0];
    float[] notEmptyArray = new float[10];
    testPredicate(emptyArray);
    testFailPredicate(notEmptyArray);
  }

  @Test
  public void testIntArrayPredicate() {
    int[] emptyArray = new int[0];
    int[] notEmptyArray = new int[10];
    testPredicate(emptyArray);
    testFailPredicate(notEmptyArray);
  }

  @Test
  public void testLongArrayPredicate() {
    long[] emptyArray = new long[0];
    long[] notEmptyArray = new long[10];
    testPredicate(emptyArray);
    testFailPredicate(notEmptyArray);
  }

  @Test
  public void testObjectArrayPredicate() {
    Object[] emptyArray = new Object[0];
    Object[] notEmptyArray = new Object[10];
    testPredicate(emptyArray);
    testFailPredicate(notEmptyArray);
  }

  @Test
  public void testShortArrayPredicate() {
    short[] emptyArray = new short[0];
    short[] notEmptyArray = new short[10];
    testPredicate(emptyArray);
    testFailPredicate(notEmptyArray);
  }

  @Test
  public void testStringPredicate() {
    testPredicate("");
    testFailPredicate("a");
  }

  @Test
  public void testCharSequencePredicate() {
    CharSequence emptyObject = mock(CharSequence.class);
    CharSequence notEmptyObject = mock(CharSequence.class);
    when(emptyObject.length()).thenReturn(0);
    when(notEmptyObject.length()).thenReturn(10);
    testPredicate(emptyObject);
    testFailPredicate(notEmptyObject);
  }

  @Test
  public void testCollectionPredicate() {
    Collection emptyObject = mock(Collection.class);
    Collection notEmptyObject = mock(Collection.class);
    when(emptyObject.isEmpty()).thenReturn(true);
    when(notEmptyObject.isEmpty()).thenReturn(false);
    testPredicate(emptyObject);
    testFailPredicate(notEmptyObject);
  }

  @Test
  public void testMapPredicate() {
    Map emptyObject = mock(Map.class);
    Map notEmptyObject = mock(Map.class);
    when(emptyObject.isEmpty()).thenReturn(true);
    when(notEmptyObject.isEmpty()).thenReturn(false);
    testPredicate(emptyObject);
    testFailPredicate(notEmptyObject);
  }

  @Test(expected = NullPointerException.class)
  public void testNullObject() {
    testFailPredicate(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotMappedObject() {
    testFailPredicate(new Object());
  }

}
