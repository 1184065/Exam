package com.example.projectexam.presentation

import androidx.lifecycle.LiveData
import com.example.projectexam.domain.entity.TopRatingEntity
import com.example.projectexam.presentation.state.TopRatingState

interface TopRatingHomeView {

    fun onShowLoading()
    fun onHideLoading()

    fun onSuccess(entity: TopRatingEntity)
    fun onError(error: Throwable)

    fun onPaginationSuccess(entity: TopRatingEntity)
    fun onPaginationError(error: Throwable)
    fun getApiTopRating()
    val states: LiveData<TopRatingState>
}