package online.newspaper.backend.controllers;

import online.newspaper.backend.dto.AuthResponse;
import online.newspaper.backend.dto.LoginRequest;
import online.newspaper.backend.dto.RegisterRequest;
import online.newspaper.backend.models.Person;
import online.newspaper.backend.security.JWTUtil;
import online.newspaper.backend.security.PersonDetails;
import online.newspaper.backend.services.PersonService;
import online.newspaper.backend.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthController(PersonValidator personValidator, PersonService personService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.personValidator = personValidator;
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
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Ошибка авторизации");
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
