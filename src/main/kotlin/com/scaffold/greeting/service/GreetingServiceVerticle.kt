package com.scaffold.greeting.service

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.get

class GreetingServiceVerticle : AbstractVerticle() {

    override fun start(startPromise: Promise<Void>?) {
        vertx.eventBus().consumer<JsonObject>("greetingService") { event ->
            val name = event.body().get<String>("name")
            event.reply(JsonObject(mapOf(Pair("Hello", name))))
        }
    }

}