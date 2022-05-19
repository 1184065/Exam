package com.example.projectexam.domain.repository

import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.entity.LatestGameEntity
import io.reactivex.Single

interface LatestGameRepository {

    fun getApiLatest(param: HomeParam): Single<LatestGameEntity>
}