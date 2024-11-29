import com.example.noticiasapi.app.domain.model.News
import com.example.noticiasapi.app.domain.model.NewsDetail

interface NewsRepository
{
    suspend fun getNews(): List<News>
    suspend fun getNewsDetail(newsId: Int): NewsDetail
}
