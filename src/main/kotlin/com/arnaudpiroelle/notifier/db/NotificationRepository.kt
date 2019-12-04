package com.arnaudpiroelle.notifier.db

import com.arnaudpiroelle.notifier.model.Agent
import com.arnaudpiroelle.notifier.model.Status
import com.arnaudpiroelle.notifier.model.Task
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class NotificationRepository {

    private val subject = BehaviorSubject.create<Status>()

    fun status(): Flowable<Status> {
        return subject.toFlowable(BackpressureStrategy.LATEST)
    }

    fun update(newAgent: Agent) {
        val status = subject.value ?: Status()
        status.agents[newAgent.name] = newAgent
        subject.onNext(status)
    }
}
