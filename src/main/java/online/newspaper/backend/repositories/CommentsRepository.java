package online.newspaper.backend.repositories;

import online.newspaper.backend.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Integer> {

//    List<Comment> findByArticleIdOrderByCreatedAtDesc(int articleId);

    Page<Comment> findByArticleIdOrderByCreatedAtDesc(int articleId, Pageable pageable);

    int countByArticleId(int articleId);
}
