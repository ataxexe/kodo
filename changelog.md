# Changelog

## v3.1.0

### Major Changes

- Added `#beNull` method to `Expectation` class.
- Added `#expect` for booleans and functions that returns a boolean value
- Refactor in `Expectation` class:
  * Renamed `#value` to `#the`
  * Renamed `#exec` to `#the`
  * Removed `#otherwise` method

## v3.0.1 (2017-02-02)

Fixed the uploaded jar /o\

## v3.0.0 (2017-02-02)

This release contains a huge facelift in Kodo. The DSL is now more simple but yet powerful.

## Major Changes

- Changed the Scenario DSL to only use `expect` method names for tests
- Added a way of defining specs without target objects
- Classes renamed:
  * Scenario -> SpecDefinition
  * TestScenario -> Spec
  * Spec -> Expectation
- Removed `Predicates` and `Reason` classes
- Removed `eachOne` predicate in favor of a nested Spec evaluation
- Removed `EmptyPredicate`
- Added a Function wrapper for using in exceptions tests
- Removed `should` method, with the new DSL, only the `to` method is sufficient
- Added a `follow` method to define nested specs

## Minor Changes

- Added `fail` method
- Fixed dummy typos in docs
- Minor code improvements
- Added a `begin` builder method for defining specs without a specific target
- Changed access to private for `Spec` constructor (use the static builder methods instead)

## v2.1.0 (2016-09-12)

- Added `#not(Predicate)` method to the `Spec` class

## v2.0.0 (2016-08-18)

- DSL changes

## v1.1.0 (2016-08-17)

- Fix generic types so compiler will not freak out
- Add a `Spec#test` method

## v1.0.1 (2016-05-08)

- Fix a NPE when the target is null

## v1.0 (31-12-2014)

None significant.

## v0.4 (18-07-2014)

- Package name changed to `tools.devnull.kodo`
- groupId changed to `tools.devnull`

## v0.3 (14-07-2014)

- Helpers moved to `Spec` class
- Empty predicates for common objects (arrays, maps, collections, strings, ...)
- Added more methods to Scenario

## v0.2 (12-06-2014)

- DSL refactoring
- `Should` renamed to `Spec` and a new method `should` was added to it to
  improve the DSL
- Messages to specs
- No more Junit dependency for compile

## v0.1 (02-06-2014)

Initial release.