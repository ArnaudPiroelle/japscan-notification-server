package com.arnaudpiroelle.notifier.server

import com.arnaudpiroelle.notifier.db.NotificationRepository
import com.arnaudpiroelle.notifier.server.service.NotificationService
import io.grpc.ServerBuilder
import java.util.logging.Level
import java.util.logging.Logger

class NotificationServer(private val port: Int) {

    private val notificationRepository = NotificationRepository()
    private val server = ServerBuilder.forPort(port)
        .addService(NotificationService(notificationRepository))
        .build()

    fun start() {
        server.start()
        server.listenSockets.forEach {
            println(it)
        }

        logger.log(Level.INFO, "Server started, listening on $port")
    }

    fun stop() {
        server.shutdown()
        logger.log(Level.INFO, "Server stopped")
    }

    fun await() {
        server.awaitTermination()
    }

    companion object {
        private val logger = Logger.getLogger(NotificationServer::class.java.name)
    }
}