package online.newspaper.backend.controllers;

import online.newspaper.backend.models.Person;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {



    @PostMapping("/login")
    public void performLogin() {

    }

    @PostMapping("/register")
    public void performRegistration(@ModelAttribute("person") Person person) {

    }

    @PostMapping("/logout")
    public void performLogout() {

    }


}
