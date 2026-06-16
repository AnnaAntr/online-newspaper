package online.newspaper.backend.services;

import online.newspaper.backend.models.Article;
import online.newspaper.backend.repositories.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class ArticleService {
    private final ArticlesRepository articlesRepository;

    @Autowired
    public ArticleService(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    // поиск одной статьи
    public Article findArticle(int id) {
        Optional<Article> foundArticle = articlesRepository.findById(id);
        return foundArticle.orElse(null);
    }

    // поиск статей на последние 24 часа с пагинацией
    public Page<Article> findFreshArticlesWithPagination(int page, int size) {
        LocalDateTime dayAgo = LocalDateTime.now().minusHours(24);
        Date dayAgoDate = Date.from(dayAgo.atZone(ZoneId.systemDefault()).toInstant());
        Pageable pageable = PageRequest.of(page, size);

        return articlesRepository.findByCreatedAtAfterOrderByCreatedAtDesc(dayAgoDate, pageable);
    }

    // поиск архивных статей
    public Page<Article> findArchiveArticlesWithPagination(int page, int size) {
        LocalDateTime dayAgo = LocalDateTime.now().minusHours(24);
        Date dayAgoDate = Date.from(dayAgo.atZone(ZoneId.systemDefault()).toInstant());
        Pageable pageable = PageRequest.of(page, size);

        return articlesRepository.findByCreatedAtBeforeOrderByCreatedAtDesc(dayAgoDate, pageable);
    }
}
