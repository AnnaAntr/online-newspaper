package online.newspaper.backend.repositories;

import online.newspaper.backend.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ArticlesRepository extends JpaRepository<Article, Integer> {

    Page<Article> findByCreatedAtAfterOrderByCreatedAtDesc(Date time, Pageable pageable);

    Page<Article> findByCreatedAtBeforeOrderByCreatedAtDesc(Date time, Pageable pageable);
}
