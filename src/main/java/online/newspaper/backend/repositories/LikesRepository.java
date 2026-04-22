package online.newspaper.backend.repositories;

import online.newspaper.backend.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Like, Integer> {
}
