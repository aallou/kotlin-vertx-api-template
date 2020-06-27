package com.scaffold.greeting

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class TestMainVerticle {

  @BeforeEach
  fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
    vertx.deployVerticle(MainVerticle(), testContext.succeeding<String> { _ -> testContext.completeNow() })
  }

  @Test
  fun verticle_deployed(vertx: Vertx, testContext: VertxTestContext) {
    testContext.completeNow()
  }

  @Test
  fun say_hello(vertx: Vertx, testContext: VertxTestContext) {
    vertx.deployVerticle(MainVerticle(), testContext.succeeding { id ->
      val client: WebClient = WebClient.create(vertx)
      client.get(8888, "localhost", "/greeting")
        .send(testContext.succeeding { response ->
          testContext.verify {
            Assertions.assertEquals(response.body().toString(), ("{\"hello\":\"world\"}"))
            testContext.completeNow()
          }
        })
    })

  }
}
