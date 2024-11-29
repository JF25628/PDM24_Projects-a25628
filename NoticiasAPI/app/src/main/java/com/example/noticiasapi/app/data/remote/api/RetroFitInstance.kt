package com.example.noticiasapi.app.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance
{
    private const val BASE_URL = "https://api.worldnewsapi.com/"

    //propriadade que inicializa a interface atraves do retrofit, lazy garante que so começa quando for acessa pela primeira vez.
    val api: NewsAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPI::class.java)
    }
}
