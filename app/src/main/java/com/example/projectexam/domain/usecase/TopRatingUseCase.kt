package com.example.projectexam.domain.usecase

import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.common.Usecase
import com.example.projectexam.domain.entity.TopRatingEntity
import com.example.projectexam.domain.executor.JobExecutor
import com.example.projectexam.domain.executor.UIThread
import com.example.projectexam.domain.repository.TopRatingRepository
import io.reactivex.Single

class TopRatingUseCase(
    private val repository: TopRatingRepository,
    executor: JobExecutor,
    thread: UIThread
) : Usecase<TopRatingEntity, HomeParam>(executor, thread) {

    override fun buildUsecaseObservable(params: HomeParam): Single<TopRatingEntity> =
        repository.getApiTopRating(params)
}
