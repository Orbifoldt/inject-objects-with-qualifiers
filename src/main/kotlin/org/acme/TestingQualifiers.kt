package org.acme

import javax.enterprise.inject.Produces
import javax.inject.Qualifier
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/qualified")
class TestingQualifiers(
    @Trees private val text: String
) {
    @GET
    fun hello() = text
}

class StringProducersQualified {
    @Produces
    @Trees
    fun createTreeString() = "oak"

    @Produces
    @Fruit
    fun fruitString() = "strawberry"
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Trees

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Fruit
