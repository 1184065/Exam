package com.example.projectexam.domain.common

import com.example.projectexam.domain.executor.PostExecutionThread
import com.example.projectexam.domain.executor.ThreadExecutor
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

abstract class UseCaseSearchGame<T, in Params>(

    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread

) {
    private val disposables = CompositeDisposable()

    protected abstract fun buildUsecaseObservableSearchGame(
        params: Params,
        keyword: String
    ): Single<T>

    fun execute(observer: DefaultObserver<T>, params: Params, keyword: String) {
        buildUsecaseObservableSearchGame(params, keyword)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
            .subscribeWith(observer)
            .addTo(disposables)
    }

    fun dispose() {
        disposables.clear()
    }
}
