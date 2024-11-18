package com.example.noticiasapi.app.data.remote.api

import com.example.noticiasapi.app.data.remote.model.NewsDetailDto
import com.example.noticiasapi.app.data.remote.model.NewsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Locale.IsoCountryCode

interface NewsAPI {
    @GET("v1/news")
    suspend fun getNews(): List<NewsDto>

    @GET("v1/news/{id}")
    suspend fun getNewsDetail(@Path("id") newsId: String) : NewsDetailDto

    @GET("v2/top-headlines")
    suspend fun getTopHeadLines(
        @Query("apiKey") apiKey : String,
        @Query("country") country : String

    )

    @GET("search-news")
    suspend fun getUSNews(
        @Query("api-Key") apiKey: String,
        @Query("source-country") sourceCountry: String,
        @Query("language") language: String
    ): Response<NewsResponse>



}