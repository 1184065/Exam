package com.example.projectexam.domain.repository

import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.entity.TopRatingEntity
import io.reactivex.Single

interface TopRatingRepository {
    fun getApiTopRating(param: HomeParam): Single<TopRatingEntity>
}