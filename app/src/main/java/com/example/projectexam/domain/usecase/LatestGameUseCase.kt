package com.example.projectexam.domain.usecase

import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.common.Usecase
import com.example.projectexam.domain.entity.LatestGameEntity
import com.example.projectexam.domain.executor.JobExecutor
import com.example.projectexam.domain.executor.UIThread
import com.example.projectexam.domain.repository.LatestGameRepository
import io.reactivex.Single

class LatestGameUseCase(
    private val repository: LatestGameRepository,
    executor: JobExecutor,
    thread: UIThread
) : Usecase<LatestGameEntity, HomeParam>(executor, thread) {

    override fun buildUsecaseObservable(params: HomeParam): Single<LatestGameEntity> =
        repository.getApiLatest(params)
}