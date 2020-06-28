package com.scaffold.greeting

import com.scaffold.greeting.resource.ResourceVerticle
import com.scaffold.greeting.service.GreetingServiceVerticle
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class TestResourceVerticle {

  @BeforeEach
  fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
    vertx.deployVerticle(ResourceVerticle(), testContext.succeeding<String> { _ -> testContext.completeNow() })
  }

  @Test
  fun verticle_deployed(vertx: Vertx, testContext: VertxTestContext) {
    testContext.completeNow()
  }

  @Test
  fun say_hello_error(vertx: Vertx, testContext: VertxTestContext) {
    vertx.deployVerticle(ResourceVerticle(), testContext.succeeding { id ->
      val client: WebClient = WebClient.create(vertx)
      client.get(8888, "localhost", "/greeting")
        .send(testContext.succeeding { response ->
          testContext.verify {
            Assertions.assertEquals(response.body().toString(), ("{\"error\":\"sorry there is a problem\"}"))
            testContext.completeNow()
          }
        })
    })
  }

  @Test
  fun say_hello_success(vertx: Vertx, testContext: VertxTestContext) {
    vertx.deployVerticle(GreetingServiceVerticle())
    vertx.deployVerticle(ResourceVerticle(), testContext.succeeding { id ->
      val client: WebClient = WebClient.create(vertx)
      client.get(8888, "localhost", "/greeting")
        .send(testContext.succeeding { response ->
          testContext.verify {
            Assertions.assertEquals(response.body().toString(), ("{\"Hello\":\"world\"}"))
            testContext.completeNow()
          }
        })
    })
  }
}
