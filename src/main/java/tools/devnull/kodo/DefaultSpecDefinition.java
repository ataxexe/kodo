package tools.devnull.kodo;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static tools.devnull.kodo.Expectation.throwAssertionError;

/**
 * The default implementation of a SpecDefinition.
 */
public class DefaultSpecDefinition<T> implements SpecDefinition<T> {

  private final String description;
  private final T target;
  private final Consumer defaultFailOperation;

  DefaultSpecDefinition(String description, T target, Consumer<?> defaultFailOperation) {
    this.description = description;
    this.target = target;
    this.defaultFailOperation = defaultFailOperation;
  }

  DefaultSpecDefinition(T target) {
    this("", target, throwAssertionError());
  }

  DefaultSpecDefinition(String description) {
    this(description, null, throwAssertionError());
  }

  DefaultSpecDefinition() {
    this("", null, throwAssertionError());
  }

  private void test(Predicate predicate, Object object, Consumer consumer) {
    if (!predicate.test(object)) {
      consumer.accept(object);
    }
  }

  @Override
  public SpecDefinition<T> onFail(Consumer<?> operation) {
    return new DefaultSpecDefinition<>(this.description, this.target, operation);
  }

  @Override
  public <R> SpecDefinition<R> given(R object) {
    return new DefaultSpecDefinition(this.description, object, this.defaultFailOperation);
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
    splitter.apply(target)
        .forEach(e -> spec.accept(new DefaultSpecDefinition(this.description, e, this.defaultFailOperation)));
    return this;
  }

  @Override
  public SpecDefinition<T> expect(Consumer<? super T> operation, Predicate<Throwable> test) {
    return expect(operation, test, this.defaultFailOperation);
  }

  @Override
  public <E> SpecDefinition<T> expect(Function<? super T, E> function, Predicate<? super E> test) {
    return expect(function, test, this.defaultFailOperation);
  }

  @Override
  public SpecDefinition<T> expect(Function<? super T, Boolean> function) {
    return expect(function, this.defaultFailOperation);
  }

  @Override
  public SpecDefinition<T> expect(boolean value) {
    return expect(value, this.defaultFailOperation);
  }

}
