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

  private void test(Predicate predicate, Object object, Consumer consumer) {
    if (!predicate.test(object)) {
      consumer.accept(object);
    }
  }

  private Consumer throwAssertionError() {
    return object -> {
      throw new AssertionError(String.format("for value: %s", object));
    };
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
  public SpecDefinition<T> expect(Consumer operation, Predicate test, Consumer consumer) {
    try {
      operation.accept(target);
      test(test, null, consumer);
    } catch (Throwable t) {
      test(test, t, consumer);
    }
    return this;
  }

  @Override
  public SpecDefinition<T> when(Runnable operation) {
    operation.run();
    return this;
  }

  @Override
  public <E> SpecDefinition<T> expect(Function<? super T, E> function,
                                      Predicate<? super E> test,
                                      Consumer<E> consumer) {
    test(test, function.apply(target), consumer);
    return this;
  }

  @Override
  public SpecDefinition<T> expect(Function<? super T, Boolean> function, Consumer<Boolean> consumer) {
    test(o -> o == Boolean.TRUE, function.apply(target), consumer);
    return this;
  }

  @Override
  public SpecDefinition<T> expect(boolean value, Consumer<Boolean> consumer) {
    test(o -> o == Boolean.TRUE, value, consumer);
    return this;
  }

  @Override
  public <E> SpecDefinition<T> each(Class<E> type, Function<T, Iterable<E>> splitter, Consumer<SpecDefinition<E>> spec) {
    splitter.apply(target).forEach(e -> spec.accept(new DefaultSpecDefinition<>(this.description, e)));
    return this;
  }

  @Override
  public SpecDefinition<T> expect(Consumer<? super T> operation, Predicate<Throwable> test) {
    return expect(operation, test, throwAssertionError());
  }

  @Override
  public <E> SpecDefinition<T> expect(Function<? super T, E> function, Predicate<? super E> test) {
    return expect(function, test, throwAssertionError());
  }

  @Override
  public SpecDefinition<T> expect(Function<? super T, Boolean> function) {
    return expect(function, throwAssertionError());
  }

  @Override
  public SpecDefinition<T> expect(boolean value) {
    return expect(value, throwAssertionError());
  }
}
