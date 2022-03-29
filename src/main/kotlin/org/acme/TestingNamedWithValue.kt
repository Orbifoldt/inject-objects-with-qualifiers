package org.acme

import javax.enterprise.inject.Produces
import javax.inject.Named
import javax.ws.rs.GET
import javax.ws.rs.Path


@Path("/named-with-value")
class TestingNamedWithValue(
    @Named("tree") private val text: String
) {
    @GET
    fun hello() = text
}

class StringProducersNamedWithValue {
    @Produces
    @Named("tree")
    fun createString1() = "maple"

    @Produces
    @Named("fruit")
    fun createString2() = "kiwi"
}