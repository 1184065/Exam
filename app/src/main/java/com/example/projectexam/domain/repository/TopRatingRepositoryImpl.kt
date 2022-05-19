package com.example.projectexam.domain.repository

import com.example.projectexam.data.factory.HomeFactory
import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.entity.TopRatingEntity
import io.reactivex.Single

class TopRatingRepositoryImpl(
    private val factory: HomeFactory
) : TopRatingRepository {

    override fun getApiTopRating(param: HomeParam): Single<TopRatingEntity> =
        factory.getApiTopRating(param.page).map { response ->
            TopRatingEntity(
                results = response.results?.map { result ->
                    TopRatingEntity.Result(
                        backgroundImage = result.backgroundImage ?: " ",
                        nametop = result.name ?: " ",
                        rating = result.rating ?: " ",
                    )
                }?.toMutableList() ?: mutableListOf()
            )
        }
}