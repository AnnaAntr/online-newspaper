package online.newspaper.backend.services;

import online.newspaper.backend.models.Person;
import online.newspaper.backend.repositories.PeopleRepository;
import online.newspaper.backend.security.PersonDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PersonDetailsServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PersonDetailsService personDetailsService;

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

    @Test
    void loadUserByUsername_WhenUserExists_ShouldReturnUserDetails() {
        String email = "ivanov@test.com";

        when(peopleRepository.findByEmail(email)).thenReturn(Optional.of(testPerson));

        UserDetails result = personDetailsService.loadUserByUsername(email);

        assertNotNull(result);
        assertTrue(result instanceof PersonDetails);

        PersonDetails personDetails = (PersonDetails) result;

        assertEquals(testPerson.getEmail(), personDetails.getEmail());
        assertEquals(testPerson.getPassword(), personDetails.getPassword());
        assertEquals(testPerson.getId(), personDetails.getPerson().getId());
        assertEquals(testPerson.getName(), personDetails.getPerson().getName());
        assertEquals(testPerson.getSurname(), personDetails.getPerson().getSurname());

        verify(peopleRepository, times(1)).findByEmail(email);
    }

    @Test
    void loadUserByUsername_WhenUserDoesNotExist_ShouldThrowException() {
        String email = "email@test.com";

        when(peopleRepository.findByEmail(email)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> personDetailsService.loadUserByUsername(email));

        assertEquals("Пользователь не найден", exception.getMessage());

        verify(peopleRepository, times(1)).findByEmail(email);
    }
}
