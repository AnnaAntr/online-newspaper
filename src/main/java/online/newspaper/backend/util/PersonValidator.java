package online.newspaper.backend.util;

import online.newspaper.backend.models.Person;
import online.newspaper.backend.repositories.PeopleRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PeopleRepository peopleRepository;

    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (peopleRepository.findByEmail(person.getEmail()).isPresent())
            errors.rejectValue("email", "", "Email уже зарегистрирован");
    }
}
