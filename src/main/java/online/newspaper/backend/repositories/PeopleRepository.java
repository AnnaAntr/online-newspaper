package online.newspaper.backend.repositories;

import online.newspaper.backend.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByEmail(String email);

    boolean existsByEmail(String email);
}
