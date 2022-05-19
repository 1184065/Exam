package com.example.projectexam.presentation.state

import com.example.projectexam.data.response.latestgame.LatestGameResponse

sealed class LatestGameState {

    object Loading : LatestGameState()

    data class Success(val response: LatestGameResponse) : LatestGameState()
    data class Error(val error: Throwable) : LatestGameState()
}