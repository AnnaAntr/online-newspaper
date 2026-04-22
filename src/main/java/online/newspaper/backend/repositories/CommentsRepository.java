package online.newspaper.backend.repositories;

import online.newspaper.backend.models.Article;
import online.newspaper.backend.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByArticleIdOrderByCreatedAtDesc(int article_id);

}
