package com.scaffold.greeting

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class MainVerticle : AbstractVerticle() {

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

    router["/greeting"]
      .handler { req: RoutingContext ->
        req.response()
          .putHeader("content-type", "application/json")
          .end(JsonObject("{\"hello\": \"world\"}").encode())
      }

    return router
  }
}
