package com.example.grpc.chat

import com.arnaudpiroelle.notifier.Notifier
import com.arnaudpiroelle.notifier.NotifierServiceGrpc
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import java.util.concurrent.TimeUnit

fun main() {
    val channel = ManagedChannelBuilder.forAddress("192.168.2.14", 9090)
        .usePlaintext()
        .build()

    val notifierService = NotifierServiceGrpc.newStub(channel)

    val res = notifierService.subscribe(
        Notifier.SubscribeRequest.getDefaultInstance(),
        object : StreamObserver<Notifier.ProgressResponse> {
            override fun onNext(value: Notifier.ProgressResponse?) {
                println(value)
            }

            override fun onError(t: Throwable?) {
                t?.printStackTrace()
            }

            override fun onCompleted() {

            }

        })

    channel.awaitTermination(60, TimeUnit.SECONDS)
}