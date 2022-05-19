package com.example.projectexam.presentation

import androidx.lifecycle.LiveData
import com.example.projectexam.domain.entity.SearchGameEntity
import com.example.projectexam.presentation.state.SearchGameState

interface SearchGameHomeView {

    fun onShowLoading()
    fun onHideLoading()

    fun onSuccess(entity: SearchGameEntity)
    fun onError(error: Throwable)

    fun onPaginationSuccess(entity: SearchGameEntity)
    fun onPaginationError(error: Throwable)
    fun getApiSearch(keyword: String)
    val searchGameState: LiveData<SearchGameState>
}