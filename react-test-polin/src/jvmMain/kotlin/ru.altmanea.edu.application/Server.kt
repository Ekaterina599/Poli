package ru.altmanea.edu.application

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.Netty
import io.ktor.server.routing.*
import kotlinx.html.*

fun HTML.index() {
    head {
        title("Hello from Ktor")
    }
    body {
        script(src = "/static/react-test.js") {}
    }
}

fun main() {
    embeddedServer(
        Netty, port = 8080, host = "127.0.0.1",
        watchPaths = listOf("classes", "distributions")
    ) {
        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            static("/static") {
                resources()
            }
        }
    }.start(wait = true)
}