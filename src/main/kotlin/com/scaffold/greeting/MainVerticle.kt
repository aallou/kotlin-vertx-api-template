package com.scaffold.greeting

import com.scaffold.greeting.router.HttpRouter
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise

class MainVerticle(val httpRouter : HttpRouter = HttpRouter()) : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val port = config().getInteger("http.port", 8080)
    vertx
      .createHttpServer()
      .requestHandler(httpRouter.router(vertx))
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
