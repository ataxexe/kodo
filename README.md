# Overview

Kodo is a test framework that helps you defining specifications with the help of a fluent interface.

## How To Build

Just make sure you have [Gradle][] and run the command `gradle dist`. To simply build the jar files, execute the command `gradle jar` and to install **kodo** on your local maven repository, use the command `gradle install`.

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
  .expect(it(), to().be(valid()), otherwise("the validation failed"));
  
Spec.given(someObject)
  .when(itExecutes())
  .expect(it(), to().be(valid()), because("the process should not invalidate the object"));
~~~

Here is more examples:

~~~java
Spec.given(new Orange())
  .expect(Orange::color, to().be("orange")
  .expect(it(), to().be(fresh()))
  .when(Orange::squeeze)
  .expect(it(), to().not().be(fresh())));
  
// defining exceptions
Spec.given(someObject)
  .expect(doingForbiddenStuff(), to().raise(IllegalStateException.class))
  .expect(doingForbiddenStuff(), to().fail())
  .expect(doingAllowedStuff(), to().succeed());


// using a collection
Spec.given(oranges)
  .each(Orange.class, spec -> spec
      .expect(it(), to().be(fresh())));

// you can also define nested specs
Spec.given(car)
  .expect(it(), to().be(NEW))
  .expect(car::engine, to().follow(spec -> spec.
      expect(it(), to().have(highPower()))));
~~~

You can always use the helper class `Expectation`. It contain a set of useful methods to help you write your awesome specifications!

# How To Contribute

Fork it, fire an issue, spread the project, use the project... any help will be great! And, please, let me know if you're liking Kodo (or not).

[gradle]: <http://gradle.org>
