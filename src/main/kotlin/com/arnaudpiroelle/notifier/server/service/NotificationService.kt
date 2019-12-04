package com.arnaudpiroelle.notifier.server.service

import com.arnaudpiroelle.notifier.Notifier
import com.arnaudpiroelle.notifier.Notifier.*
import com.arnaudpiroelle.notifier.RxNotifierServiceGrpc
import com.arnaudpiroelle.notifier.db.NotificationRepository
import com.arnaudpiroelle.notifier.model.Agent
import com.arnaudpiroelle.notifier.model.Status
import com.arnaudpiroelle.notifier.model.Task
import io.reactivex.Flowable
import io.reactivex.Single

class NotificationService(
    private val notificationRepository: NotificationRepository
) :
    RxNotifierServiceGrpc.NotifierServiceImplBase() {

    override fun notify(request: Single<NotifyRequest>): Single<NotifyResponse> {
        return request
            .map { it.agent.asAgent() }
            .doOnSuccess(notificationRepository::update)
            .map {
                NotifyResponse.newBuilder()
                    .setSuccess(true)
                    .build()
            }
    }

    override fun subscribe(request: Single<SubscribeRequest>): Flowable<ProgressResponse>? {
        println("new client subscribed")
        return request
            .toFlowable()
            .flatMap { notificationRepository.status() }
            .map(Status::asProgress)
    }

}

private fun Notifier.Agent.asAgent(): Agent {
    return Agent(name, downloadedManga, totalManga, tasksList.map(Notifier.Task::asTask).associateBy { it.name })
}

private fun Notifier.Task.asTask(): Task {
    return Task(name, manga, chapter, currentChapter, totalChapter, currentPage, totalPage)
}

fun Status.asProgress(): ProgressResponse = ProgressResponse.newBuilder()
    .addAllAgents(agents.values.map { it.asAgent() })
    .build()

fun Agent.asAgent(): Notifier.Agent = Notifier.Agent.newBuilder()
    .setName(name)
    .setDownloadedManga(downloadedManga)
    .setTotalManga(totalManga)
    .addAllTasks(tasks.values.map { it.asTask() })
    .build()

fun Task.asTask(): Notifier.Task = Notifier.Task.newBuilder()
    .setName(name)
    .setManga(manga)
    .setChapter(chapter)
    .setCurrentChapter(currentChapter)
    .setTotalChapter(totalChapter)
    .setCurrentPage(currentPage)
    .setTotalPage(totalPage)
    .build()