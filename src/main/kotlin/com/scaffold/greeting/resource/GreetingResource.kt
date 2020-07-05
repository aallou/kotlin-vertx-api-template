package com.scaffold.greeting.resource

import com.scaffold.greeting.service.GreetingService
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext

class GreetingResource (greetingService : GreetingService) {

    val sayHello : (routingContext : RoutingContext) -> Unit = { routingContext ->
        val message = greetingService.sayHello(routingContext.queryParam("name").getOrElse(0, { "world" }))
        routingContext.response()
            .putHeader("content-type", "application/json")
            .end(JsonObject("{\"message\": \"$message\"}").encode())
    }
}