package tools.devnull.kodo;

/**
 * Interface that improves readability by exposing methods to declare messages.
 */
public interface Reason {

  /**
   * Helper method to improve code readability. It returns the given string.
   * <p>
   * Use it with the methods that takes a message.
   */
  static String because(String reason) {
    return reason;
  }

  /**
   * Helper method to improve code readability. It returns the given string.
   * <p>
   * Use it with the methods that takes a message.
   */
  static String otherwise(String description) {
    return description;
  }

}
