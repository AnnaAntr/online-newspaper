package online.newspaper.backend.repositories;

import online.newspaper.backend.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Like, Integer> {

    int countByArticleId(int articleId);

    Optional<Like> findByArticleIdAndAuthorId(int articleId, int personId);
}
