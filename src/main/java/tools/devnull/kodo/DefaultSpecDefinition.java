/*
 * The MIT License
 *
 * Copyright (c) 2014 Marcelo "Ataxexe" Guimarães <ataxexe@devnull.tools>
 *
 * Permission  is hereby granted, free of charge, to any person obtaining
 * a  copy  of  this  software  and  associated  documentation files (the
 * "Software"),  to  deal  in the Software without restriction, including
 * without  limitation  the  rights to use, copy, modify, merge, publish,
 * distribute,  sublicense,  and/or  sell  copies of the Software, and to
 * permit  persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * The  above  copyright  notice  and  this  permission  notice  shall be
 * included  in  all  copies  or  substantial  portions  of the Software.
 *
 * THE  SOFTWARE  IS  PROVIDED  "AS  IS",  WITHOUT  WARRANTY OF ANY KIND,
 * EXPRESS  OR  IMPLIED,  INCLUDING  BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN  NO  EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM,  DAMAGES  OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT  OR  OTHERWISE,  ARISING  FROM,  OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE   OR   THE   USE   OR   OTHER   DEALINGS  IN  THE  SOFTWARE.
 */

package tools.devnull.kodo;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static tools.devnull.kodo.Expectation.throwAssertionError;

/**
 * The default implementation of a SpecDefinition.
 */
public class DefaultSpecDefinition<T> implements SpecDefinition<T> {

  final String description;
  final T target;
  final Consumer defaultFailOperation;

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
  public <R> SpecDefinition<R> given(Function<T, R> function) {
    return new DefaultSpecDefinition<>(this.description, function.apply(this.target), this.defaultFailOperation);
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
    } catch (Exception e) {
      test(test, e, consumer);
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
  public <E> SpecDefinition<T> expect(Supplier<E> supplier, Predicate<? super E> test) {
    return expect(supplier, test, this.defaultFailOperation);
  }

  @Override
  public <E> SpecDefinition<T> expect(Supplier<E> supplier, Predicate<? super E> test, Consumer<E> consumer) {
    test(test, supplier.get(), consumer);
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
  public SpecDefinition<T> expect(Consumer<? super T> operation, Predicate<? extends Exception> test) {
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
