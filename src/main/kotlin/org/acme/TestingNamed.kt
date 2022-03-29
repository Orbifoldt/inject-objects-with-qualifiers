package org.acme

import javax.enterprise.inject.Produces
import javax.inject.Named
import javax.ws.rs.GET
import javax.ws.rs.Path


@Path("/named")
class TestingNamed(
    @Named("someFruit") private val text: String
) {
    @GET
    fun hello() = text
}

class StringProducersNamed {
    @Produces
    @Named
    fun myTree() = "redwood"

    @Produces
    @Named
    fun someFruit() = "grape"
}