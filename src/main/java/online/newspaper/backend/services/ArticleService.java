package online.newspaper.backend.services;

import online.newspaper.backend.models.Article;
import online.newspaper.backend.repositories.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArticleService {
    private final ArticlesRepository articlesRepository;

    @Autowired
    public ArticleService(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public Article findArticle(int id) {
        Optional<Article> foundArticle = articlesRepository.findById(id);
        return foundArticle.orElse(null);
    }

    public List<Article> findFreshArticles() {
        LocalDateTime dayAgo = LocalDateTime.now().minusHours(24);
        return articlesRepository.findByCreatedAtAfterOrderByCreatedAtDesc(dayAgo);
    }

    public List<Article> findArchiveArticles() {
        LocalDateTime dayAgo = LocalDateTime.now().minusHours(24);
        return articlesRepository.findByCreatedAtBeforeOrderByCreatedAtDesc(dayAgo);
    }
}
