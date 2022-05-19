package com.example.projectexam.presentation.presenter

import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.common.DefaultObserver
import com.example.projectexam.domain.entity.TopRatingEntity
import com.example.projectexam.domain.usecase.TopRatingUseCase
import com.example.projectexam.presentation.TopRatingHomeView

class TopRatingPresenter(
    private val view: TopRatingHomeView,
    private val usecase: TopRatingUseCase
) {

    fun getApiTopRating() {
        view.onShowLoading()
        usecase.execute(
            GetApiTopRatingUsecase(),
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

    inner class GetApiTopRatingUsecase : DefaultObserver<TopRatingEntity>() {

        override fun onSuccess(entity: TopRatingEntity) {
            view.onHideLoading()
            view.onSuccess(entity)
        }

        override fun onError(exception: Throwable) {
            view.onHideLoading()
            view.onError(exception)
        }
    }

    inner class LoadMoreUsecase : DefaultObserver<TopRatingEntity>() {

        override fun onSuccess(entity: TopRatingEntity) {
            view.onPaginationSuccess(entity)
        }

        override fun onError(exception: Throwable) {
            view.onPaginationError(exception)
        }
    }
}