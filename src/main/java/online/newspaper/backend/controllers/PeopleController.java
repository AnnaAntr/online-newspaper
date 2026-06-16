//package online.newspaper.backend.controllers;
//
//import online.newspaper.backend.models.Person;
//import online.newspaper.backend.services.PersonService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/users")
//public class PeopleController {
//
//    private final PersonService personService;
//
//    @Autowired
//    public PeopleController(PersonService personService) {
//        this.personService = personService;
//    }
//
//    @PostMapping
//    public Person createPerson(@RequestBody Person person) {
//        return personService.createPerson(person);
//    }
//
//    @GetMapping("/{id}")
//    public Person getOnePerson(@PathVariable("id") int id) {
//        return personService.findPerson(id);
//    }
//}
