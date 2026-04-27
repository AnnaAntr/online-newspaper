package online.newspaper.backend.repositories;

import online.newspaper.backend.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticlesRepository extends JpaRepository<Article, Integer> {

/*    List<Article> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime time);

    List<Article> findByCreatedAtBeforeOrderByCreatedAtDesc(LocalDateTime time);*/

    Page<Article> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime time, Pageable pageable);

    Page<Article> findByCreatedAtBeforeOrderByCreatedAtDesc(LocalDateTime time, Pageable pageable);
}
