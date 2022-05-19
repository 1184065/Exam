package com.example.projectexam.domain.repository

import com.example.projectexam.data.factory.HomeFactory
import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.entity.SearchGameEntity
import io.reactivex.Single

class SearchGameRepositoryImpl(
    private val factory: HomeFactory
) : SearchGameRepository {

    override fun getApiSearch(param: HomeParam, keyword: String): Single<SearchGameEntity> =

        factory.getApiSearch(keyword).map { response ->
            SearchGameEntity(
                results = response.results?.map { result ->
                    SearchGameEntity.Result(
                        backgroundImage = result.backgroundImage ?: " ",
                        name = result.name ?: " ",
                        rating = result.rating ?: " ",
                    )
                }?.toMutableList() ?: mutableListOf()
            )
        }
}