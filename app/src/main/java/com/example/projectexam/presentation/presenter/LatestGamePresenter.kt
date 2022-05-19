package com.example.projectexam.presentation.presenter

import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.common.DefaultObserver
import com.example.projectexam.domain.entity.LatestGameEntity
import com.example.projectexam.domain.usecase.LatestGameUseCase
import com.example.projectexam.presentation.LatestGameHomeView

class LatestGamePresenter(
    private val view: LatestGameHomeView,
    private val usecase: LatestGameUseCase
) {

    fun getApiLatest() {
        view.onShowLoading()
        usecase.execute(
            GetApiLatestUsecase(),
            HomeParam()
        )
    }

    fun loadMore(page: Long) {
        usecase.execute(
            LoadMoreUsecase(),
            HomeParam(page = page)
        )
    }

    fun onDetach() {
        usecase.dispose()
    }

    inner class GetApiLatestUsecase : DefaultObserver<LatestGameEntity>() {

        override fun onSuccess(entity: LatestGameEntity) {
            view.onHideLoading()
            view.onSuccess(entity)
        }

        override fun onError(exception: Throwable) {
            view.onHideLoading()
            view.onError(exception)
        }
    }

    inner class LoadMoreUsecase : DefaultObserver<LatestGameEntity>() {

        override fun onSuccess(entity: LatestGameEntity) {
            view.onPaginationSuccess(entity)
        }

        override fun onError(exception: Throwable) {
            view.onPaginationError(exception)
        }
    }
}