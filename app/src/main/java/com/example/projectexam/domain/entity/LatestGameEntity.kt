package com.example.projectexam.domain.entity

data class LatestGameEntity(
    val results: MutableList<Result>
) {

    data class Result(
        val backgroundImage: String,
        val namelatest: String,
        val released: String,
    )
}