package com.example.grpc.chat

import com.arnaudpiroelle.notifier.Notifier
import com.arnaudpiroelle.notifier.NotifierServiceGrpc
import io.grpc.ManagedChannelBuilder
import java.net.InetAddress

fun main() {
    val channel = ManagedChannelBuilder.forAddress("192.168.2.14", 9090)
        .usePlaintext()
        .build()

    val hostname = InetAddress.getLocalHost().hostName
    val name = Thread.currentThread().name

    println(name)

    val notifierService = NotifierServiceGrpc.newBlockingStub(channel)
    val notifyResponse = notifierService.notify(
        Notifier.NotifyRequest.newBuilder()
            .setAgent(
                Notifier.Agent.newBuilder()
                    .setName("MacOs")
                    .setDownloadedManga(1500)
                    .setTotalManga(2000)
                    .addTasks(
                        Notifier.Task.newBuilder()
                            .setName("Thread 1")
                            .setManga("One Piece")
                            .setChapter("Chap. 999 : Hello")
                            .setCurrentChapter(1)
                            .setTotalChapter(999)
                            .setCurrentPage(1)
                            .setTotalPage(20)
                            .build()
                    )
                    .build()
            )
            .build()
    )
    println(notifyResponse)
}