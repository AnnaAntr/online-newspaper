package online.newspaper.backend.repositories;

import online.newspaper.backend.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
