package com.example.projectexam.data.source

import com.example.projectexam.BuildConfig.API_KEY
import com.example.projectexam.data.response.latestgame.LatestGameResponse
import com.example.projectexam.data.response.search.SearchGameResponse
import com.example.projectexam.model.TopRatingResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface HomeDatasource {
    @GET("games?key=${API_KEY}&page_size=10&ordering=-rating&platforms=4&page=1")
    fun getApiTopRating(
        @Query("api_key")
        apiKey: String,

        @Query("page")
        page: Long
    ): Single<TopRatingResponse>

    @GET("games?key=${API_KEY}&page_size=10&ordering=-released&platforms=4&page=1&dates=2021-12-01,2021-12-31")
    fun getApiLatest(
        @Query("api_key")
        apiKey: String,
    )
            : Single<LatestGameResponse>

    @GET("games?key=${API_KEY}&page_size=10&platforms=4&page=1")
    fun getApiSearch(
        @Query("api_key")
        apiKey: String,

        @Query("search")
        keyword: String
    ): Single<SearchGameResponse>
}
