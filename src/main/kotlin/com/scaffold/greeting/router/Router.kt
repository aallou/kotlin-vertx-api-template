package com.scaffold.greeting.router

import com.scaffold.greeting.resource.GreetingResource
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

fun router(vertx: Vertx) : Router {
    val router = Router.router(vertx)
    router.get("/greeting").handler(GreetingResource().sayHello)
    return router
}