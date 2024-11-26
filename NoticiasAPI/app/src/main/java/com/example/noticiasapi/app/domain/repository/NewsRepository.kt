import android.adservices.adid.AdId
import com.example.noticiasapi.app.domain.model.News
import com.example.noticiasapi.app.domain.model.NewsDetail
import org.intellij.lang.annotations.Language

interface NewsRepository{
    suspend fun getNews(): List<News>
    suspend fun getNewsDetail(newsId: Int): NewsDetail
}
