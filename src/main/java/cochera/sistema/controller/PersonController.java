package cochera.sistema.controller;


import cochera.sistema.dto.CarToPersonDto;
import cochera.sistema.entity.Car;
import cochera.sistema.entity.Person;
import cochera.sistema.exception.UserNotFoundException;
import cochera.sistema.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/person")

public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/all")
    public Iterable<Person> getAllPerson(){
        return personService.listarPerson();
    }

    @PostMapping("/add")
    public void addPerson(@RequestBody Person person){
        personService.newPerson(person);
    }

    @PutMapping("/update")
    public void updatePerson(@RequestBody Person person){
        personService.updatePerson(person);
    }

    @PostMapping("/add-to-car")
    public ResponseEntity<?> addCarPerson (@Valid @RequestBody CarToPersonDto carToPersonDto) {
        personService.addCarToPerson(carToPersonDto.getDni(), carToPersonDto.getRegistrationplate());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/remove-to-car")
    public ResponseEntity<?> removeCarPerson (@Valid @RequestBody CarToPersonDto carToPersonDto) {
        personService.removeCarToPerson(carToPersonDto.getDni(), carToPersonDto.getRegistrationplate());
        return ResponseEntity.ok().build();
    }

    @GetMapping("find/{dni}")
    public Optional<Person> getById(@PathVariable("dni") String dni) {
        return personService.searchPerson(dni);
    }

    @GetMapping("/count")
    public Long countPerson(){
        return personService.countPerson();
    }

    @GetMapping("/cars/{dni}")
    Collection<Car> getCars(@PathVariable("dni") String dni){
        return personService.searchCars(dni);
    }

    @PutMapping("/status")
    public ResponseEntity<?> changeUserStatus(@RequestParam String dni,
                                              @RequestParam boolean isEnabled) {
        if (dni == null || dni.isBlank()) throw new UserNotFoundException("EL dni del conductor es obligatorio!");
        return personService.changeUserStatus(dni, isEnabled);
    }



}

