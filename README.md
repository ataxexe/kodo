# Overview

Kodo is a test framework that helps you creating test scenarios with the help of a fluent interface.

## How To Build

Just make sure you have [Gradle][] and run the command `gradle dist`. To simply build the jar files, execute the command `gradle jar` and to install **kodo** on your local maven repository, use the command `gradle install`.

## How to Use

Just put the really small jar file on your classpath with JUnit binaries. You can also install **kodo** on your local repository and then define it on your pom or gradle build:

- **groupId:** org.kodo
- **artifactId:** kodo

# Creating Test Scenarios

To create a new test scenario, use the `TestScenario` helper class. It defines an entry point to the fluent interface:

~~~java
TestScenario.given(someObject);
~~~

With the `Scenario` interface returned, you can use a set of methods to describe behaviours:

~~~java
TestScenario.given(someObject)
  .when(obj -> obj.foo())
  .thenIt(Should.be(obj -> obj.isValid());
~~~

This can be refactored to a more elegant code:

~~~java
TestScenario.given(someObject)
  .when(itExecutes()) // an extracted method
  .thenIt(Should.be(valid()); // an extracted method
~~~

Here is some real example:

~~~java
TestScenario.given(element("name").in(annotation()))
  .the(Element::value, Should.be("some name"))
  .the(Element::name, Should.be("name"))
  .it(Should.NOT_BE_NULL.andThen(Should.notBe(writable())))
  .then(attempToChangeValue(), Should.raise(HandlingException.class));

// using a collection
TestScenario.given(elements().in(annotation()))
  .thenIt(Should.NOT_BE_EMPTY)
  .each(Should.notBe(writable())) // \
  .each(Should.be(readable()))    //  > iterates through all elements
  .each(shouldHaveAValue());      // /
~~~

You can always use the helper class `Should`. It contains a set of useful methods to help you write your tests.

# How To Contribute

Fork it, fire an issue, spread the project, use the project... any help will be great! And let me know if you're liking (or not).

[gradle]: <http://gradle.org>