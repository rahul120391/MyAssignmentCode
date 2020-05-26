package com.example.gojek.networking.cleanarchitecturebase

import android.os.Handler
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class UseCaseThreadPoolScheduler : UseCaseScheduler {

    private val POOL_SIZE = 6

    private val MAX_POOL_SIZE = 8

    private val TIME_OUT = 30

    private val mHandler = Handler()

    private var mThreadPoolExecutor: ThreadPoolExecutor

    init {
        mThreadPoolExecutor = ThreadPoolExecutor(
                POOL_SIZE, MAX_POOL_SIZE, TIME_OUT.toLong(),
                TimeUnit.SECONDS, ArrayBlockingQueue(POOL_SIZE)
        )
    }

    override fun execute(runnable: Runnable) {
        mThreadPoolExecutor.execute(runnable)
    }

    override fun <V : UseCase.ResponseValue> notifyResponse(
            response: V,
            useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        mHandler.post { useCaseCallback.onSuccess(response) }
    }

    override fun <V : UseCase.ResponseValue> onError(
            useCaseCallback: UseCase.UseCaseCallback<V>, t: String?
    ) {
        mHandler.post { useCaseCallback.onError(t) }
    }

}