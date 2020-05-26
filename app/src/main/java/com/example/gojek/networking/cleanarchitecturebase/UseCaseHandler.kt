package com.example.gojek.networking.cleanarchitecturebase

class UseCaseHandler(
        private val useCaseScheduler: UseCaseScheduler
) {

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(
            useCase: UseCase<T, R>, values: T, callback: UseCase.UseCaseCallback<R>
    ) {
        useCase.requestValues = values
        useCase.useCaseCallback = UiCallbackWrapper(callback, this)

        useCaseScheduler.execute(Runnable {
            useCase.run()
        })
    }

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> subscribe(
            useCase: UseCase<T, R>, values: T, callback: UseCase.UseCaseCallback<R>
    ) {
        useCase.requestValues = values
        useCase.useCaseCallback = UiCallbackSubscribeWrapper(callback, this)

        useCase.run()
    }

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> stopSubscription(
            useCase: UseCase<T, R>
    ) {
        useCase.stopSubscription()
    }


    private fun <V : UseCase.ResponseValue> notifySubscribeResponse(
            response: V,
            useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        useCaseCallback.onSuccess(response)
    }

    private fun <V : UseCase.ResponseValue> notifySubscribeError(
            useCaseCallback: UseCase.UseCaseCallback<V>, t: String?
    ) {
        useCaseCallback.onError(t)
    }

    private fun <V : UseCase.ResponseValue> notifyResponse(
            response: V,
            useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        useCaseScheduler.notifyResponse(response, useCaseCallback)
    }

    private fun <V : UseCase.ResponseValue> notifyError(
            useCaseCallback: UseCase.UseCaseCallback<V>, t: String?
    ) {
        useCaseScheduler.onError(useCaseCallback, t)
    }

    private class UiCallbackWrapper<V : UseCase.ResponseValue>(
            private val mCallback: UseCase.UseCaseCallback<V>,
            private val mUseCaseHandler: UseCaseHandler
    ) : UseCase.UseCaseCallback<V> {

        override fun onSuccess(response: V) {
            mUseCaseHandler.notifyResponse(response, mCallback)
        }

        override fun onError(t: String?) {
            mUseCaseHandler.notifyError(mCallback, t)
        }
    }

    private class UiCallbackSubscribeWrapper<V : UseCase.ResponseValue>(
            private val mCallback: UseCase.UseCaseCallback<V>,
            private val mUseCaseHandler: UseCaseHandler
    ) : UseCase.UseCaseCallback<V> {

        override fun onSuccess(response: V) {
            mUseCaseHandler.notifySubscribeResponse(response, mCallback)
        }

        override fun onError(t: String?) {
            mUseCaseHandler.notifySubscribeError(mCallback, t)
        }
    }

    companion object {

        private var INSTANCE: UseCaseHandler? = null
        fun getInstance(): UseCaseHandler? {
            if (INSTANCE == null) {
                INSTANCE = UseCaseHandler(UseCaseThreadPoolScheduler())
            }
            return INSTANCE
        }
    }
}