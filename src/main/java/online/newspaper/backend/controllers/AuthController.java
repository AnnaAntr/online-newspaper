package online.newspaper.backend.controllers;

import online.newspaper.backend.dto.AuthResponse;
import online.newspaper.backend.dto.LoginRequest;
import online.newspaper.backend.dto.RegisterRequest;
import online.newspaper.backend.models.Person;
import online.newspaper.backend.security.JWTUtil;
import online.newspaper.backend.security.PersonDetails;
import online.newspaper.backend.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    public AuthController(PersonService personService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateToken(loginRequest.getEmail());

            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            Person person = personDetails.getPerson();

            return ResponseEntity.ok(new AuthResponse(
                    jwt,
                    person.getId(),
                    person.getEmail(),
                    person.getName(),
                    person.getSurname()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
        } catch (AuthenticationException e) {
            logger.log(Level.WARNING, "Ошибка аутентификации для " + loginRequest.getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка аутентификации");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> performRegistration(@Valid @RequestBody RegisterRequest registerRequest) {
        if (personService.checkPersonExistsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Пользователь с таким email уже существует");
        }

        Person person = new Person();
        person.setName(registerRequest.getName());
        person.setSurname(registerRequest.getSurname());
        person.setEmail(registerRequest.getEmail());
        person.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        personService.createPerson(person);

        return ResponseEntity.ok("Регистрация прошла успешно");
    }
}
