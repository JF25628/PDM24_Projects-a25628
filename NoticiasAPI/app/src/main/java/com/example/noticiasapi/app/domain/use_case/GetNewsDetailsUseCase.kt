import com.example.noticiasapi.app.domain.model.NewsDetail

class GetNewsDetailsUseCase(private val repository: NewsRepository){
    suspend operator fun invoke(newsId: Int): NewsDetail {
        return repository.getNewsDetail(newsId)
    }
}