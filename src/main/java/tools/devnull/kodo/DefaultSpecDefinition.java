package tools.devnull.kodo;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The default implementation of a SpecDefinition.
 */
public class DefaultSpecDefinition<T> implements SpecDefinition<T> {

  private final String description;
  private final T target;

  DefaultSpecDefinition(String description, T target) {
    this.description = description;
    this.target = target;
  }

  DefaultSpecDefinition(T target) {
    this("", target);
  }

  DefaultSpecDefinition(String description) {
    this(description, null);
  }

  DefaultSpecDefinition() {
    this("", null);
  }

  private void test(Predicate predicate, Object target, String message) {
    if (!predicate.test(target)) {
      throw new AssertionError(message == null ? defaultMessage(target) : message);
    }
  }

  private String defaultMessage(Object target) {
    return String.format("for value: %s", target);
  }

  @Override
  public <R> SpecDefinition<R> given(R object) {
    return new DefaultSpecDefinition<>(this.description, object);
  }

  @Override
  public SpecDefinition begin() {
    return new DefaultSpecDefinition<T>(this.description);
  }

  @Override
  public SpecDefinition<T> when(Consumer<? super T> operation) {
    operation.accept(target);
    return this;
  }

  @Override
  public SpecDefinition<T> expect(Consumer operation, Predicate test, String message) {
    try {
      operation.accept(target);
      test(test, null, message);
    } catch (Throwable t) {
      test(test, t, message);
    }
    return this;
  }

  @Override
  public SpecDefinition<T> when(Runnable operation) {
    operation.run();
    return this;
  }

  @Override
  public <E> SpecDefinition<T> expect(Function<? super T, E> function, Predicate<? super E> test, String message) {
    test(test, function.apply(target), message);
    return this;
  }

  @Override
  public SpecDefinition<T> expect(Function<? super T, Boolean> function, String message) {
    test(o -> o == Boolean.TRUE, function.apply(target), message);
    return this;
  }

  @Override
  public SpecDefinition<T> expect(boolean value, String message) {
    test(o -> o == Boolean.TRUE, value, message);
    return this;
  }

  @Override
  public <E> SpecDefinition<T> each(Class<E> type, Function<T, Iterable<E>> splitter, Consumer<SpecDefinition<E>> spec) {
    splitter.apply(target).forEach(e -> spec.accept(new DefaultSpecDefinition<>(this.description, e)));
    return this;
  }

}
