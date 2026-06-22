package online.newspaper.backend.services;

import online.newspaper.backend.models.Person;
import online.newspaper.backend.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PersonService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    // поиск пользователя по id
//    public Person findPerson(int id) {
//        Optional<Person> foundUser = peopleRepository.findById(id);
//        return foundUser.orElse(null);
//    }

    // проверка существования пользователя по email
    public boolean checkPersonExistsByEmail(String email) {
        return peopleRepository.existsByEmail(email);
    }

    // создание пользователя
    public Person createPerson(Person person) {
        return peopleRepository.save(person);
    }
}
