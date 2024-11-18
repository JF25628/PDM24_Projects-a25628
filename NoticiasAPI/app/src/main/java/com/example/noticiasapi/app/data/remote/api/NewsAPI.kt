package com.example.noticiasapi.app.data.remote.api

import com.example.noticiasapi.app.data.remote.model.NewsDetailDto
import com.example.noticiasapi.app.data.remote.model.NewsDetailResponse
import com.example.noticiasapi.app.data.remote.model.NewsDto
import com.example.noticiasapi.app.data.remote.model.NewsResponse
import com.example.noticiasapi.app.domain.model.NewsDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Locale.IsoCountryCode

interface NewsAPI {

    @GET("top-news")
    suspend fun getNews(
        @Query("source-country") sourceCountry: String,
        @Query("language") language: String,
        @Query("api-key") apiKey: String
    ): NewsResponse

    @GET("retrieve-news")
    suspend fun getNewsDetail(
        @Query("ids") newsId: Int,
        @Query("api-key") apikey: String
    ): NewsDetailResponse

}