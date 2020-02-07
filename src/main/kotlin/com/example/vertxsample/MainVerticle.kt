package com.example.vertxsample

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

/**
 * Main Verticle.
 */
class MainVerticle : AbstractVerticle() {
    override fun start(startFuture: Future<Void>?) {
        val router = createRouter()

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8080)
    }

    private fun createRouter(): Router {
        return Router.router(vertx).apply {
            get("/").handler(handlerRoot)
        }
    }

    val handlerRoot = Handler<RoutingContext> {
        req -> req.response().end("welcome")
    }
}