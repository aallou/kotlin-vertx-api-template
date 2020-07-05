package com.scaffold.greeting.service

class GreetingService {

    fun sayHello(name: String = "world") : String {
        return "Hello $name"
    }
}