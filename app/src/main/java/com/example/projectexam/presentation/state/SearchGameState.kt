package com.example.projectexam.presentation.state

import com.example.projectexam.data.response.search.SearchGameResponse

sealed class SearchGameState {

    object Loading : SearchGameState()

    data class Success(val response: SearchGameResponse) : SearchGameState()
    data class Error(val error: Throwable) : SearchGameState()
}