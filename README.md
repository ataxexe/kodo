# Overview

Kodo is a test framework that helps you creating test scenarios with the help of a fluent interface.

## How To Build

Just make sure you have [Gradle][] and run the command `gradle dist`. To simply build the jar files, execute the command `gradle jar` and to install **kodo** on your local maven repository, use the command `gradle install`.

## How to Use

Just put the really small kodo jar file on your classpath. You can also install **kodo** on your local repository and then define it on your pom or gradle build:

- **groupId:** tools.devnull
- **artifactId:** kodo

Kodo is also in Maven Central. Just use the above `groupId` and `artifactId` to declare the dependency.

# Creating Test Scenarios

To create a new test scenario, use the `TestScenario` helper class. It defines an entry point to the fluent interface:

~~~java
TestScenario.given(someObject);
~~~

With the `Scenario` interface returned, you can use a set of methods to describe behaviours:

~~~java
TestScenario.given(someObject)
  .when(obj -> obj.foo())
  .expect(it(), obj -> obj.isValid());
~~~

This can be refactored to a more elegant code:

~~~java
TestScenario.given(someObject)
  .when(itExecutes()) // an extracted method
  .expect(it(), to().be(valid())); // an extracted method
~~~

And messages may be supplied:

~~~java
TestScenario.given(someObject)
  .when(itExecutes())
  .expect(it(), to().be(valid()), otherwise("the validation failed"));
  
TestScenario.given(someObject)
  .when(itExecutes())
  .expect(it(), to().be(valid()), because("the process should not invalidate the object"));
~~~

Here is more examples:

~~~java
TestScenario.given(new Orange())
  .expect(Orange::color, to().be("orange")
  .expect(it(), to().be(fresh()))
  .when(Orange::squeeze)
  .expect(it(), to().not().be(fresh())));
  
// defining exceptions
TestScenario.given(someObject)
  .expect(doingForbiddenStuff(), to().raise(IllegalStateException.class))
  .expect(doingForbiddenStuff(), to().fail())
  .expect(doingAllowedStuff(), to().succeed());
~~~

You can always use the helper classes `Spec` and `Predicates`. They contain a set of useful methods to help you 
write your awesome tests!

# How To Contribute

Fork it, fire an issue, spread the project, use the project... any help will be great! And let me know if you're liking (or not).

[gradle]: <http://gradle.org>
