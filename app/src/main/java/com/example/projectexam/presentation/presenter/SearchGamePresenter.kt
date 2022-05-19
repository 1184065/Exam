package com.example.projectexam.presentation.presenter

import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.common.DefaultObserver
import com.example.projectexam.domain.entity.SearchGameEntity
import com.example.projectexam.domain.usecase.SearchGameUseCase
import com.example.projectexam.presentation.SearchGameHomeView


class SearchGamePresenter(
    private val view: SearchGameHomeView,
    private val usecase: SearchGameUseCase
) {
    fun getApiSearch(keyword: String) {
        view.onShowLoading()
        usecase.execute(
            GetApiSearchUsecase(),
            HomeParam(),
            keyword
        )
    }


    fun loadMore(page: Long, keyword: String) {
        usecase.execute(
            LoadMoreUsecase(),
            HomeParam(page = page),
            keyword
        )
    }

    fun onDetach() {
        usecase.dispose()
    }

    inner class GetApiSearchUsecase : DefaultObserver<SearchGameEntity>() {

        override fun onSuccess(entity: SearchGameEntity) {
            view.onHideLoading()
            view.onSuccess(entity)
        }

        override fun onError(exception: Throwable) {
            view.onHideLoading()
            view.onError(exception)
        }
    }

    inner class LoadMoreUsecase : DefaultObserver<SearchGameEntity>() {

        override fun onSuccess(entity: SearchGameEntity) {
            view.onPaginationSuccess(entity)
        }

        override fun onError(exception: Throwable) {
            view.onPaginationError(exception)
        }
    }
}