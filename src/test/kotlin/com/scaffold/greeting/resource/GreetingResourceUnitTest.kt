package com.scaffold.greeting.resource

import com.scaffold.greeting.MainVerticle
import com.scaffold.greeting.router.HttpRouter
import com.scaffold.greeting.service.GreetingService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class GreetingResourceUnitTest {

    @MockK
    lateinit var greetingService: GreetingService

    private val port = 8081

    init {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
    }

    @BeforeEach
    fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
        val options = DeploymentOptions()
            .setConfig(
                JsonObject().put("http.port", port)
            )
        vertx.deployVerticle(MainVerticle(HttpRouter()), options, testContext.succeeding<String> { _ -> testContext.completeNow() })
    }

    @Test
    fun given_nameIsEmpty_then_sayHelloToWorld(vertx: Vertx, testContext: VertxTestContext) {
        every { greetingService.sayHello() } returns "Hello world"

        val client: WebClient = WebClient.create(vertx)
        client.get(port, "localhost", "/greeting")
            .send(testContext.succeeding { response ->
                testContext.verify {
                    Assertions.assertEquals(response.body().toString(), ("{\"message\":\"Hello world\"}"))
                    testContext.completeNow()
                }
            })
    }

}