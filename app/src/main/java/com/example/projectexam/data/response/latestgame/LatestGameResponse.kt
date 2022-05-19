package com.example.projectexam.data.response.latestgame

import com.google.gson.annotations.SerializedName

data class LatestGameResponse(
    @SerializedName("results")
    val results: List<LatestGame>? = emptyList()
)

data class LatestGame(
    @SerializedName("background_image")
    val backgroundImage: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("released")
    val release: String? = null,
)