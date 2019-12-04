package com.arnaudpiroelle.notifier

import com.arnaudpiroelle.notifier.server.NotificationServer


fun main() {

    val server = NotificationServer(9090)
    server.start()

    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            server.stop()
        }
    })

    server.await()
}