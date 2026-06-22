package online.newspaper.backend.services;

import online.newspaper.backend.models.Person;
import online.newspaper.backend.repositories.PeopleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PersonService personService;

    private Person testPerson;

    @BeforeEach
    void setUp() {
        testPerson = new Person();
        testPerson.setId(1);
        testPerson.setName("Ivan");
        testPerson.setSurname("Ivanov");
        testPerson.setEmail("ivanov@test.com");
        testPerson.setPassword("ivanov_password123");
    }

    // checkPersonExistsByEmail ------------------------------------------------------
    @Test
    void checkPersonExistsByEmail_WhenEmailExists_ShouldReturnTrue() {
        String email = "ivanov@test.com";

        when(peopleRepository.existsByEmail(email)).thenReturn(true);

        boolean result = personService.checkPersonExistsByEmail(email);

        assertTrue(result);

        verify(peopleRepository, times(1)).existsByEmail(email);
    }

    @Test
    void checkPersonExistsByEmail_WhenEmailDoesNotExist_ShouldReturnFalse() {
        String email = "ivanov@test.com";

        when(peopleRepository.existsByEmail(email)).thenReturn(false);

        boolean result = personService.checkPersonExistsByEmail(email);

        assertFalse(result);

        verify(peopleRepository, times(1)).existsByEmail(email);
    }

    @Test
    void checkPersonExistsByEmail_WithEmptyEmail_ShouldReturnFalse() {
        String email = "";

        when(peopleRepository.existsByEmail(email)).thenReturn(false);

        boolean result = personService.checkPersonExistsByEmail(email);

        assertFalse(result);

        verify(peopleRepository, times(1)).existsByEmail(email);
    }

    // createPerson ----------------------------------------------------------------
    @Test
    void createPerson_ShouldSaveAndReturnPerson() {
        Person personToSave = new Person();
        personToSave.setName("Petr");
        personToSave.setSurname("Petrov");
        personToSave.setEmail("petrov@test.ru");
        personToSave.setPassword("petrov_password");

        Person savedPerson = new Person();
        savedPerson.setId(2);
        savedPerson.setName(personToSave.getName());
        savedPerson.setSurname(personToSave.getSurname());
        savedPerson.setEmail(personToSave.getEmail());
        savedPerson.setPassword(personToSave.getPassword());

        when(peopleRepository.save(any(Person.class))).thenReturn(savedPerson);

        Person result = personService.createPerson(personToSave);

        assertNotNull(result);
        assertEquals(savedPerson.getId(), result.getId());
        assertEquals(savedPerson.getName(), result.getName());
        assertEquals(savedPerson.getSurname(), result.getSurname());
        assertEquals(savedPerson.getEmail(), result.getEmail());
        assertEquals(savedPerson.getPassword(), result.getPassword());

        verify(peopleRepository, times(1)).save(any(Person.class));
    }

    @Test
    void checkPersonExistsByEmail_AfterCreation_ShouldReturnTrue() {
        String email = "new@test.com";

        Person personToSave = new Person();
        personToSave.setName("Vasiliy");
        personToSave.setSurname("Vasiliev");
        personToSave.setEmail(email);
        personToSave.setPassword("vasiliev_password");

        Person savedPerson = new Person();
        savedPerson.setId(5);
        savedPerson.setName(personToSave.getName());
        savedPerson.setSurname(personToSave.getSurname());
        savedPerson.setEmail(email);
        savedPerson.setPassword(personToSave.getPassword());

        when(peopleRepository.save(any(Person.class))).thenReturn(savedPerson);
        when(peopleRepository.existsByEmail(email)).thenReturn(true);

        personService.createPerson(personToSave);
        boolean personExists = personService.checkPersonExistsByEmail(email);

        assertTrue(personExists);

        verify(peopleRepository, times(1)).save(any(Person.class));
        verify(peopleRepository, times(1)).existsByEmail(email);
    }
}
