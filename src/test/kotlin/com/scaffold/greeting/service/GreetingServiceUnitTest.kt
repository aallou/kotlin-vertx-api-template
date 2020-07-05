package com.scaffold.greeting.service

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GreetingServiceUnitTest {

    val greetingService = GreetingService()

    @Test
    fun given_nameIsEmpty_then_sayHelloWorld() {
        assertEquals(greetingService.sayHello(), "Hello world")
    }

    @Test
    fun given_name_then_sayHelloToName() {
        assertEquals(greetingService.sayHello("Kotlin"), "Hello Kotlin")
    }
}