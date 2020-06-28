package com.scaffold.greeting

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise

class Main : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>?) {
    vertx.deployVerticle("com.scaffold.greeting.resource.ResourceVerticle")
    vertx.deployVerticle("com.scaffold.greeting.service.GreetingServiceVerticle")
  }
}
