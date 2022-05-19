package com.example.projectexam.domain.usecase

import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.common.UseCaseSearchGame
import com.example.projectexam.domain.entity.SearchGameEntity
import com.example.projectexam.domain.executor.JobExecutor
import com.example.projectexam.domain.executor.UIThread
import com.example.projectexam.domain.repository.SearchGameRepository
import io.reactivex.Single

class SearchGameUseCase(
    private val repository: SearchGameRepository,
    executor: JobExecutor,
    thread: UIThread
) : UseCaseSearchGame<SearchGameEntity, HomeParam>(executor, thread) {

    override fun buildUsecaseObservableSearchGame(
        params: HomeParam,
        keyword: String
    ): Single<SearchGameEntity> =
        repository.getApiSearch(params, keyword)
}
