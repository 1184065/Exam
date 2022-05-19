package com.example.projectexam.domain.entity

data class TopRatingEntity(
    val results: MutableList<Result>
) {

    data class Result(
        val backgroundImage: String,
        val nametop: String,
        val rating: String,

        )
}
