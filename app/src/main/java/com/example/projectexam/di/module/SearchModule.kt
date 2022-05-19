package com.example.projectexam.di.module

import androidx.lifecycle.ViewModel
import com.example.projectexam.data.factory.HomeFactory
import com.example.projectexam.data.source.HomeDatasource
import com.example.projectexam.di.scope.Presentation
import com.example.projectexam.di.scope.ViewModelKey
import com.example.projectexam.domain.executor.JobExecutor
import com.example.projectexam.domain.executor.UIThread
import com.example.projectexam.domain.repository.SearchGameRepository
import com.example.projectexam.domain.repository.SearchGameRepositoryImpl
import com.example.projectexam.domain.usecase.SearchGameUseCase
import com.example.projectexam.presentation.SearchGameHomeView
import com.example.projectexam.presentation.activity.SearchActivity
import com.example.projectexam.presentation.presenter.SearchGamePresenter
import com.example.projectexam.presentation.viewmodel.SearchGameViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {

    companion object {

        @Presentation
        @Provides
        fun providesHomeDataSource(retrofit: Retrofit): HomeDatasource =
            retrofit.create(HomeDatasource::class.java)

        @Presentation
        @Provides
        fun providesFactory(datasource: HomeDatasource): HomeFactory =
            HomeFactory(datasource)

        @Presentation
        @Provides
        fun providesSearchGameRepository(factory: HomeFactory): SearchGameRepositoryImpl =
            SearchGameRepositoryImpl(factory)

        @Presentation
        @Provides
        fun providesSearchGameUseCase(
            repository: SearchGameRepository,
            executor: JobExecutor,
            thread: UIThread
        ): SearchGameUseCase = SearchGameUseCase(repository, executor, thread)

        @Presentation
        @Provides
        fun providesSearchGameViewModel(
            view: SearchGameHomeView,
            usecase: SearchGameUseCase
        ): SearchGameViewModel = SearchGameViewModel(view, usecase)
    }

    @Binds
    abstract fun bindSearchGameRepository(repositoryImpl: SearchGameRepositoryImpl): SearchGameRepository

    @Binds
    abstract fun bindSearchGameHomeView(activity: SearchActivity): SearchGameHomeView

    @Binds
    @IntoMap
    @ViewModelKey(SearchGameViewModel::class)
    abstract fun bindSearchGameViewModel(ViewModel: SearchGameViewModel): ViewModel
}