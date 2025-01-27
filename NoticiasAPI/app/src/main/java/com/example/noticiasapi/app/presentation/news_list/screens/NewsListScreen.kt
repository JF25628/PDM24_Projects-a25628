package com.example.noticiasapi.app.presentation.news_list.screens

import android.text.Html
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.noticiasapi.app.domain.model.News
import com.example.noticiasapi.app.presentation.news_list.viewModel.NewsListViewModel

fun parseHtmlToText(html: String): AnnotatedString
{
    return buildAnnotatedString {
        append(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT).toString())
    }
}

@Composable
fun NewsListScreen(viewModel: NewsListViewModel, onNewsClick: (Int) -> Unit) {
    val news = viewModel.newsList.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.fetchNews()
    }

    if (news.isEmpty()) {
        LoadingScreen2()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(news) { article ->
                NewsBox(news = article, onClick = { onNewsClick(article.id) })
            }
        }
    }
}

@Composable
fun NewsBox(news: News, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 2
            )
            if (!news.summary.isNullOrBlank()) {
                Text(
                    text = parseHtmlToText(news.summary),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp),
                    maxLines = 3
                )
            }
        }
    }
}

@Composable
fun LoadingScreen2() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)

        Text(
            text = "Loading ...",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}