# Overview

Kodo is a test framework that helps you defining specifications with the help of a fluent interface.

## How To Build

Just make sure you have [Maven][] and run the command `maven package`. To install **kodo** on your local maven repository, use the command `maven install`.

## How to Use

Just put the really small kodo jar file on your classpath. You can also install **kodo** on your local repository and then define it on your pom or gradle build:

- **groupId:** tools.devnull
- **artifactId:** kodo

Kodo is also in Maven Central. Just use the above `groupId` and `artifactId` to declare the dependency.

# Defining Specifications

To define a new specification, use the `Spec` class. It defines an entry point to the fluent interface:

~~~java
Spec.given(someObject);

Spec.begin(); // for specifications without a target 
~~~

With the `SpecDefinition` interface returned, you can use a set of methods to describe behaviours:

~~~java
Spec.given(someObject)
  .when(obj -> obj.foo())
  .expect(it(), obj -> obj.isValid());
~~~

This can be refactored to a more elegant code:

~~~java
Spec.given(someObject)
  .when(itExecutes()) // an extracted method
  .expect(it(), to().be(valid())); // an extracted method
~~~

And messages may be supplied:

~~~java
Spec.given(someObject)
  .when(itExecutes())
  .expect(it(), to().be(valid()), "the process should not invalidate the object");
~~~

But you can use a helper to make things fancier:

~~~java
Spec.given(someObject)
  .when(itExecutes())
  .expect(it(), to().be(valid()), because("the process should not invalidate the object"));
~~~

Here is more examples:

~~~java
Account accountA = new Account(500);
Account accountB = new Account(500);

Spec.begin()
  .expect(the(accountA), to().have(balanceOf(500)))
  .expect(the(accountB), to().have(balanceOf(500)))
  
  .when(() -> accountA.transfer(100).to(accountB))
  
  .expect(the(accountA), to().have(balanceOf(400)))
  .expect(the(accountB), to().have(balanceOf(600)))

  .expect(() -> accountA.transfer(1000).to(accountB), to().raise(InsufficientBalanceException.class))
  
  .expect(the(accountA), to().have(balanceOf(400)))
  .expect(the(accountB), to().have(balanceOf(600)));

// -------

  public static Predicate<Account> balanceOf(double value) {
    // Please don't use this comparisson in a real case scenario
    // this is just an example
    return account -> account.balance() == value;
  }
~~~

Also, take a look at the `Expectation` class. It contains a set of useful methods to help you write your awesome 
specifications!

# How To Contribute

Fork it, fire an issue, spread the project, use the project... any help will be great! And, please, let me know if you're liking Kodo (or not).

[maven]: <https://maven.apache.org>
