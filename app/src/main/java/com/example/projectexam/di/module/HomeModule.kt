package com.example.projectexam.di.module

import androidx.lifecycle.ViewModel
import com.example.projectexam.data.factory.HomeFactory
import com.example.projectexam.data.source.HomeDatasource
import com.example.projectexam.di.scope.Presentation
import com.example.projectexam.di.scope.ViewModelKey
import com.example.projectexam.domain.executor.JobExecutor
import com.example.projectexam.domain.executor.UIThread
import com.example.projectexam.domain.repository.LatestGameRepository
import com.example.projectexam.domain.repository.LatestGameRepositoryImpl
import com.example.projectexam.domain.repository.TopRatingRepository
import com.example.projectexam.domain.repository.TopRatingRepositoryImpl
import com.example.projectexam.domain.usecase.LatestGameUseCase
import com.example.projectexam.domain.usecase.TopRatingUseCase
import com.example.projectexam.presentation.LatestGameHomeView
import com.example.projectexam.presentation.TopRatingHomeView
import com.example.projectexam.presentation.activity.HomeActivity
import com.example.projectexam.presentation.presenter.LatestGamePresenter
import com.example.projectexam.presentation.presenter.TopRatingPresenter
import com.example.projectexam.presentation.viewmodel.LatestGameViewModel
import com.example.projectexam.presentation.viewmodel.TopRatingViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {

    @Module
    companion object {

        @JvmStatic
        @Presentation
        @Provides
        fun providesHomeDataSource(retrofit: Retrofit): HomeDatasource =
            retrofit.create(HomeDatasource::class.java)

        @Presentation
        @Provides
        fun providesFactory(datasource: HomeDatasource): HomeFactory =
            HomeFactory(datasource)


        //TopRatingRepoInstance
        @Presentation
        @Provides
        fun providesRepository(factory: HomeFactory): TopRatingRepositoryImpl =
            TopRatingRepositoryImpl(factory)

        //LatestGameRepoInstance
        @Presentation
        @Provides
        fun providesLatestGameRepository(factory: HomeFactory): LatestGameRepositoryImpl =
            LatestGameRepositoryImpl(factory)

        //TopRatingUseCase
        @Presentation
        @Provides
        fun providesUsecase(
            repository: TopRatingRepository,
            executor: JobExecutor,
            thread: UIThread
        ): TopRatingUseCase = TopRatingUseCase(repository, executor, thread)

        //LatestGameUseCase
        @Presentation
        @Provides
        fun providesLatestGameUseCase(
            repository: LatestGameRepository,
            executor: JobExecutor,
            thread: UIThread
        ): LatestGameUseCase = LatestGameUseCase(repository, executor, thread)

//        //TopRatingPresenter
//        @Presentation
//        @Provides
//        fun providesTopRatingViewModel(
//            view: TopRatingHomeView,
//            usecase: TopRatingUseCase
//        ): TopRatingViewModel = TopRatingViewModel(view, usecase)
//
//        //LatestGamePresenter
//        @Presentation
//        @Provides
//        fun providesLatestGameViewModel(
//            view: LatestGameHomeView,
//            usecase: LatestGameUseCase
//        ): LatestGameViewModel = LatestGameViewModel(view, usecase)
    }

    @Binds
    abstract fun bindRepository(repositoryImpl: TopRatingRepositoryImpl): TopRatingRepository

    @Binds
    abstract fun bindView(activity: HomeActivity): TopRatingHomeView

    @Binds
    @IntoMap
    @ViewModelKey(TopRatingViewModel::class)
    abstract fun bindHomeVieModel(ViewModel: TopRatingViewModel): ViewModel

    @Binds
    abstract fun bindLatestGameRepository(repositoryImpl: LatestGameRepositoryImpl): LatestGameRepository

    @Binds
    abstract fun bindLatestGameHomeView(activity: HomeActivity): LatestGameHomeView

    @Binds
    @IntoMap
    @ViewModelKey(LatestGameViewModel::class)
    abstract fun bindLatestGameViewModel(ViewModel: LatestGameViewModel): ViewModel
}