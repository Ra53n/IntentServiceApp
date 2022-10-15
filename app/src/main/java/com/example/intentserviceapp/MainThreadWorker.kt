package com.example.intentserviceapp

import java.util.*

class MainThreadWorker : Thread() {

    private var isThreadRunning = true

    private val queue: Queue<() -> Any> = LinkedList()

    override fun start() {
        super.start()
        Thread {
            while (isThreadRunning) {
                queue.poll()?.invoke()
            }
        }.start()
    }

    fun post(someWork: () -> Any) {
        queue.add {
            Thread {
                someWork.invoke()
            }.start()
        }
    }

    fun post(delay: Long, someWork: () -> Any) {
        queue.add {
            Thread {
                sleep(delay)
                someWork.invoke()
            }.start()
        }
    }

    fun stopWorker() {
        isThreadRunning = false
    }
}