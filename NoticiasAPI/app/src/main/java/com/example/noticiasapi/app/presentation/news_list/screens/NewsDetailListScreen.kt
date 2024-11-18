package com.example.noticiasapi.app.presentation.news_list.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.noticiasapi.app.presentation.news_list.viewModel.NewsDetailListViewModel

//@Composable
//fun NewsDetailScreen(
//    viewModel: NewsDetailListViewModel,
//    onBack: () -> Unit
//) {
//
//    val storyDetail = viewModel.newsDetail.collectAsState()
//
//    storyDetail.value?.let { detail ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            Text(text = detail.tittle, modifier = Modifier.padding(bottom = 8.dp))
//            Text(text = detail.text, modifier = Modifier.padding(bottom = 8.dp))
//            Text(text = detail.summary, modifier = Modifier.padding(bottom = 16.dp))
//            Button(onClick = onBack) {
//                Text("Back")
//            }
//        }
//    } ?: Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Text("Loading...")
//    }
//}



@Composable
fun NewsDetailListScreen(
    viewModel: NewsDetailListViewModel,
    articleId: Int,
    onBack: () -> Unit
) {
    val articleDetail = viewModel.newsDetail.collectAsState()

    LaunchedEffect(articleId) {
        viewModel.fetchNewsDetail(articleId)
    }

    if (articleDetail.value == null) {
        LoadingScreen1()
    } else {
        articleDetail.value?.let { detail ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = detail.title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "By ${detail.author}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Gray
                            )
                        )
                        Text(
                            text = detail.publishDate,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Gray
                            )
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = detail.text,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }

                    Button(
                        onClick = onBack,
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Back")
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen1() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "A carregar detalhes...",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Gray
            )
        )
    }
}
