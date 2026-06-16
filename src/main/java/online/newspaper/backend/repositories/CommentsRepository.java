package online.newspaper.backend.repositories;

import online.newspaper.backend.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentsRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findByArticleIdOrderByCreatedAtDesc(int articleId, Pageable pageable);

    int countByArticleId(int articleId);
}
