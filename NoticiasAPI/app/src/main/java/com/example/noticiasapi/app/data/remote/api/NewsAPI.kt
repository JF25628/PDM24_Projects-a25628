package com.example.noticiasapi.app.data.remote.api

import com.example.noticiasapi.app.data.remote.model.NewsDetailResponse
import com.example.noticiasapi.app.data.remote.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI
{
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