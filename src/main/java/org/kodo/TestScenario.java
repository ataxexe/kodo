package org.kodo;

import org.kodo.impl.ScenarioImpl;

/**
 * A class to create scenarios for testing.
 *
 * @author Marcelo Guimarães
 */
public class TestScenario {

  /**
   * Start defining a new {@link Scenario} based on the given target.
   *
   * @param object
   * @param <T>
   * @return
   */
  public static <T> Scenario<T> given(T object) {
    return new ScenarioImpl<>(object);
  }

}
