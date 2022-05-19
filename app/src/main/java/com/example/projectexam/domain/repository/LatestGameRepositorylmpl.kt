package com.example.projectexam.domain.repository

import com.example.projectexam.data.factory.HomeFactory
import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.entity.LatestGameEntity
import io.reactivex.Single

class LatestGameRepositoryImpl(
    private val factory: HomeFactory
) : LatestGameRepository {

    override fun getApiLatest(param: HomeParam): Single<LatestGameEntity> =
        factory.getApiLatest().map { response ->
            LatestGameEntity(
                results = response.results?.map { result ->
                    LatestGameEntity.Result(
                        backgroundImage = result.backgroundImage ?: " ",
                        namelatest = result.name ?: " ",
                        released = result.release ?: " ",
                    )
                }?.toMutableList() ?: mutableListOf()
            )
        }
}