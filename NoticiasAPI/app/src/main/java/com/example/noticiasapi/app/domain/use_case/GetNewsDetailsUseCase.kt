import com.example.noticiasapi.app.domain.model.NewsDetail

class GetNewsDetailsUseCase (private val repository: NewsRepository)
{
    suspend operator fun invoke (newsId: String): NewsDetail {
        return repository.getNewsDetail(newsId)
    }
}