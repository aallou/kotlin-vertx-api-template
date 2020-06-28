package com.scaffold.greeting.resource

import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext

class GreetingResource {

    val sayHello : (routingContext : RoutingContext) -> Unit = {
            routingContext ->
                routingContext.response()
                    .putHeader("content-type", "application/json")
                    .end(JsonObject("{\"hello\": \"world\"}").encode())
    }
}