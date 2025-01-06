package com.example.noticiasapi.app.data.remote.model

import com.example.noticiasapi.app.domain.model.NewsDetail
import com.google.gson.annotations.SerializedName

data class NewsDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("author") val author: String,
    @SerializedName("publish_date") val publishDate: String,
    @SerializedName("text") val text: String,
    @SerializedName("title") val title: String,
)
{
    //Metodo implementado para converter um objeto newsdetail dto num newsdetail da camada de dominio
    fun toNewsDetail(): NewsDetail
    {
        return NewsDetail(id=id, author = author, publishDate = publishDate, text = text, title = title)
    }
}