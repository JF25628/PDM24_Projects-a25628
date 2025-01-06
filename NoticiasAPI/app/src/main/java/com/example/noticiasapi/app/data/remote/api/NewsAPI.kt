package com.example.noticiasapi.app.data.remote.api

import com.example.noticiasapi.app.data.remote.model.NewsDetailResponse
import com.example.noticiasapi.app.data.remote.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

//Codigo utilizado para definir a interface da NewAPI, que obtem os dados atraves de endpoints da api de noticias
interface NewsAPI
{

    // Método para obter as notícias principais.
    @GET("top-news")
    suspend fun getNews(
        @Query("source-country") sourceCountry: String,
        @Query("language") language: String,
        @Query("api-key") apiKey: String
    ): NewsResponse

    // Método para obter os detalhes de uma notícia específica.
    @GET("retrieve-news")
    suspend fun getNewsDetail(
        @Query("ids") newsId: Int,
        @Query("api-key") apikey: String
    ): NewsDetailResponse

}