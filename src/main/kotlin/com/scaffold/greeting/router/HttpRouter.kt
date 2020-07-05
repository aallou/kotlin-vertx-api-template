package com.scaffold.greeting.router

import com.scaffold.greeting.resource.GreetingResource
import com.scaffold.greeting.service.GreetingService
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class HttpRouter (private val greetingService : GreetingService = GreetingService()) {

    fun router(vertx: Vertx): Router {
        val router = Router.router(vertx)
        router.get("/greeting").handler(GreetingResource(greetingService).sayHello)
        return router
    }

}