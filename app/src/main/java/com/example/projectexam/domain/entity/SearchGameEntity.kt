package com.example.projectexam.domain.entity

data class SearchGameEntity(
    val results: MutableList<Result>
) {

    data class Result(
        val backgroundImage: String,
        val name: String,
        val rating: String,
    )
}