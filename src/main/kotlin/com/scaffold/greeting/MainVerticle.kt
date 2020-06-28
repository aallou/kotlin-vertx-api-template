package com.scaffold.greeting

import com.scaffold.greeting.router.router
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val port = 8888
    vertx
      .createHttpServer()
      .requestHandler(router(vertx))
      .listen(port) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port $port")
        } else {
          startPromise.fail(http.cause());
        }
      }
  }
}
