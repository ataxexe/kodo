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
import org.kodo.TestScenario;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.kodo.Scenario.should;
import static org.kodo.Spec.*;

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

  private Predicate<String> characters(int chars) {
    return string -> string.length() == chars;
  }

  private Predicate<Object> beAString() {
    return obj -> obj.getClass().equals(String.class);
  }

  @Test
  public void testObjectScenario() {
    TestScenario.given("kodo is a test framework")
        .the(length(), Spec.be(24))
        .it(should(equal("kodo is a test framework")))
        .when(concat("some string that should not affect the original"))
        .the(length(), Spec.be(24))
        .the("other string", should(notBe(NULL)))
        .then("other string", should(notBe(NULL)))
        .thenIt(should(equal("kodo is a test framework")))
        .and(should(notBe(NULL)))
        .and(should(have(characters(24))))
        .and(should(notHave(characters(25))))
        .and(should(beAString()))
        .then(charAt(0), Spec.succeed())
        .and(charAt(25), Spec.raise(StringIndexOutOfBoundsException.class));
  }

  @Test
  public void testNullScenario() {
    TestScenario.given(null)
        .it(should(be(NULL)));
  }

  @Test
  public void testCollectionScenario() {
    TestScenario.given(Arrays.asList("kobo is a test framework".split("\\s")))
        .the(size(), Spec.be(5))
        .the(size(), Spec.be(greatherThan(4)))
        .each(String.class, should(notBe(NULL)))
        .each(should(notBe(NULL)));
  }

}
