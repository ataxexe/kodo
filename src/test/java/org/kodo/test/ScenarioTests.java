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
import org.kodo.TestScenario;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Marcelo Guimarães
 */
public class ScenarioTests {

  private Consumer<String> concat(String value) {
    return s -> s.concat(value);
  }

  private Function<String, Integer> length() {
    return s -> s.length();
  }

  private Consumer<String> charAt(int position) {
    return s -> s.charAt(position);
  }

  private Function<Collection, Integer> size() {
    return s -> s.size();
  }

  @Test
  public void testObjectScenario() {
    TestScenario.given("kodo is a test framework")
        .the(length(), Should.be(24))
        .it(Should.equal("kodo is a test framework"))
        .when(concat("some string that should not affect the original"))
        .the(length(), Should.be(24))
        .the("other string", Should.NOT_BE_NULL)
        .then("other string", Should.NOT_BE_NULL)
        .thenIt(Should.equal("kodo is a test framework"))
        .and(Should.NOT_BE_NULL)
        .then(charAt(0), Should.succeed())
        .and(charAt(25), Should.raise(StringIndexOutOfBoundsException.class));
  }

  @Test
  public void testCollectionScenario() {
    TestScenario.given(Arrays.asList("kobo is a test framework".split("\\s")))
        .the(size(), Should.be(5))
        .each(String.class, Should.NOT_BE_NULL)
        .each(Should.notBeNull());
  }

}
