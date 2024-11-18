package com.example.noticiasapi.app.presentation.news_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

class NewsListScreen {
}



@Composable
fun NewsListScreen(newsListViewModel: NewsListViewModel, modifier: Modifier = Modifier)
{
    val news by newsListViewModel.news.collectAsState()

    LazyColumn(modifier = modifier)
    {
        items(news) { news -> Text(new.) }
    }
}