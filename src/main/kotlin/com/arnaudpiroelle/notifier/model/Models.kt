package com.arnaudpiroelle.notifier.model

data class Status(val agents: MutableMap<String, Agent> = hashMapOf())

data class Agent(
    val name: String,
    val downloadedManga: Int = 0,
    val totalManga: Int = 0,
    val tasks: Map<String, Task> = mutableMapOf()
)

data class Task(
    val name: String,
    val manga: String = "",
    val chapter: String = "",
    val currentChapter: Int = 0,
    val totalChapter: Int = 0,
    val currentPage: Int = 0,
    val totalPage: Int = 0
)