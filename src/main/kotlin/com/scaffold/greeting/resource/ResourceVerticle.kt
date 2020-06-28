package com.scaffold.greeting.resource

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class ResourceVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    vertx
      .createHttpServer()
      .requestHandler(router())
      .listen(8888) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 8888")
        } else {
          startPromise.fail(http.cause());
        }
      }
  }

  private fun router() : Router {
    val router = Router.router(vertx)

    router.get("/greeting").handler(this::greeting)
    router.errorHandler(500, this::error)

    return router
  }

  fun greeting(routingContext: RoutingContext) {
    val name = routingContext.queryParam("name").ifEmpty { listOf("world") }.last()
    vertx.eventBus().request<JsonObject>("greetingService", JsonObject(mapOf(Pair("name", name)))) { ar ->
      if(ar.succeeded()) {
        routingContext.response()
          .putHeader("content-type", "application/json")
          .end(ar.result().body().encode())
      }
      else {
        routingContext.response()
          .putHeader("content-type", "application/json")
          .end(JsonObject("{\"error\": \"sorry there is a problem\"}").encode())
      }
    }
  }

  fun error(routingContext: RoutingContext) {
    println("Handling failure")
    routingContext.failure()?.printStackTrace()
  }
}
