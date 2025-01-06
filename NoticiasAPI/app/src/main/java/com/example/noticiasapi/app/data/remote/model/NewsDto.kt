package com.example.noticiasapi.app.data.remote.model

import com.example.noticiasapi.app.domain.model.News
import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("language") val language: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("title") val title: String,
)
{
    //Metodo implementado para converter um objeto news dto num news da camada de dominio
    fun toNews(): News
    {
        return News(id = id, title = title, summary = summary, language = language)
    }
}

