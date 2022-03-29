# Injecting objects basics

This project demonstrates simple usage the `@javax.enterprise.inject.Produces` annotation and how to distinguish such producers using qualifiers.

> :warning: **Warning**: The discussed annotations here are the default `@javax.inject.Qualifier`, `@javax.inject.Named` and `@javax.enterprise.inject.Produces` ones. In Spring the equivalents are `@org.springframework.beans.factory.annotation.Qualifier`, `@org.springframework.stereotype.Component` and `@org.springframework.context.annotation.Bean`, respectively. These do behave slightly differently, so do read the documentation carefully!

## `@Qualifier`
Firstly, in [`TestingQualifiers.kt`](TestingQualifiers.kt) in the class `StringProducersQualified` we have two producers of objects of type `String`:
```kotlin
@Produces
fun createTreeString() = "oak"

@Produces
fun fruitString() = "strawberry"
```
We want to inject a string into the `text` property of the `TestingQualifiers` class, which would then be returned when you make a GET call to [http://localhost:8080/qualified](http://localhost:8080/qualified). But a question you might ask is how would quarkus know which of the two strings to inject, either 'strawberry' or 'oak'. 

To resolve which object to inject we use qualifiers, for each object of the same type that is produced we add a qualifier annotation. Creating a qualifier is simple, the most basic example is:
```kotlin
@Qualifier
annotation class Trees
```
We simply add this qualifier `@Trees` to our producer to tell the whole universe (our JVM) that this specific producer produces trees. Similarly, you could define a `@Fruit` annotation for our other producer.

Let's now say that in your managed bean – in this case our `TestingQualifiers` class – we need a tree, not a fruit. Wherever the string is injected we now add the qualifier `@Trees`, et voila! Quarkus (or more precisely the CDI manager) makes sure the right string is injected into the field.

Explicitly, we use it like this:
```kotlin
class TestingQualifiers(
    @Trees private val text: String
)
```
And like that the `text` property will contain the value 'oak' whenever this class is instantiated.

> By the way, it's good to remember how above code is equivalent to:
> ```kotlin
> class TestingQualifiers(
>     @Inject @Trees private val text: String
> )
> ```
> but, since this is our primary constructor, the `@Inject` may be omitted.


## `@Named`
There is an alternative way to accomplish the exact same as we just saw, namely by using the `@Named` annotation. By simply adding this to a bean or your producer you can use the class/method name (with the first letter becoming lower-case) to distinguish multiple beans/objects of the same type.

An explicit example can be found in [`TestingNamed.kt`](TestingNamed.kt). Here we again have two producers
```kotlin
@Produces
@Named
fun myTree() = "redwood"

@Produces
@Named
fun someFruit() = "grape"
```
This means that we can use the name of these methods as a parameter in the `@Named` annotation at the injection point to specify which of these two strings should be injected. So, for example, we can inject 'grape' by adding `@Named("someFruit")` when we inject the string:
```kotlin
class TestingNamed(
    @Named("someFruit") private val text: String
) 
```

## `@Named("value")`
Moreover, we can pre-emptively provide a value parameter to `@Named`. This provides your bean or produced object with a name, which you can use to refer to the specific producer. For example, consider below producers:
```kotlin
@Produces
@Named("tree")
fun createString1() = "maple"

@Produces
@Named("fruit")
fun createString2() = "kiwi"
```
Now let's say we want to inject the 'kiwi' into some bean. How would we accomplish this? You might have guessed it already, we simply add `@Named("fruit")` at the point where we inject the string and then the CDI manager will know which string you wanted. Explicitly, we have:
```kotlin
class TestingNamedWithValue(
    @Named("fruit") private val text: String
)
```
which then ensure that `text` contains the value 'kiwi'! See also [`TestingNamedWithValue.kt`](TestingNamedWithValue.kt).





### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
