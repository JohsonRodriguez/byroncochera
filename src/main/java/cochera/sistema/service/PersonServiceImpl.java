package cochera.sistema.service;

import cochera.sistema.entity.Car;
import cochera.sistema.entity.Person;
import cochera.sistema.exception.UserNotFoundException;
import cochera.sistema.repository.CarRepository;
import cochera.sistema.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final CarRepository carRepository;

    public PersonServiceImpl(PersonRepository personRepository, CarRepository carRepository) {
        this.personRepository = personRepository;
        this.carRepository = carRepository;
    }


    @Autowired
    private CarServiceImpl carServiceImpl;

    public Iterable<Person> listarPerson() {
        Iterable<Person> results=  personRepository.AllPerson();
//        for (Array result : results) {
//            System.out.println(
//                    "Country: " + result.);
//        }

//        return results;
        return personRepository.AllPerson();
    }



//    public Person newPerson2(Person person) {
//        var checkPerson = personRepository.findById(person.getDni());
//        if (!checkPerson.isPresent()) {
//              personRepository.save(person);
//        } else {
//            throw new UserNotFoundException("DNI ya existe");
//        }
//        Person newPerson = new Person();
//        newPerson.setDni(person.getDni());
//        newPerson.setName(person.getName());
//        newPerson.setLastname(person.getLastname());
//        newPerson.setPhone(person.getPhone());
//        newPerson.setType(person.getType());
//        newPerson.setState(person.getState());
//        newPerson.getCars()
//                .addAll(person
//                        .getCars()
//                        .stream()
//                        .map(v -> {
//                            Car vv = carServiceImpl.findCarByRegistrationPlate(v.getRegistrationplate());
//                            vv.getPersons().add(newPerson);
//                            return vv;
//                        }).collect(Collectors.toList()));
//        return personRepository.save(newPerson);

//    }

    @Override
    public Person newPerson(Person person) {
        var checkPerson = personRepository.findByDni(person.getDni());
        if (checkPerson != null) throw new UserNotFoundException("Persona ya registrada");
        person.setEnabled(true);
        Person newPerson = null;
        try {
            return newPerson = personRepository.save(person);
        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }


    }

    @Override
    public Collection<Car> searchCars(String person_id) {
        var person =personRepository.findByDni(person_id);
        var cars = person.getCars();
        return cars;
    }

    @Override
    public void updatePerson(Person person) {
        var checkPerson = personRepository.findByDni(person.getDni());
        if (checkPerson==null) throw new UserNotFoundException("Person does'nt exists");
        checkPerson.setName(person.getName());
        checkPerson.setLastname(person.getLastname());
        checkPerson.setPhone(person.getPhone());
        checkPerson.setType(person.getType());
        try {
            personRepository.save(checkPerson);
        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }


    @Override
    public void addCarToPerson(String dni, String registrationplate) {
        var person= personRepository.findByDni(dni);
        var cars = person.getCars().size();
        if (cars > 2) throw new UserNotFoundException("Solo puede registrar 3 vehiculos por persona");
        var totalcars = person.getCars();
        for(Car p : totalcars) {
            boolean check = p.getRegistrationplate().contains(registrationplate);
            if (check) throw new UserNotFoundException("Vehículo ya está registrado, seleccione otro vehiculo");
        }
        var car = carRepository.findByregistrationplate(registrationplate);
        person.getCars().add(car);

    }

    @Override
    public void removeCarToPerson(String dni, String registrationplate) {
        var person= personRepository.findByDni(dni);
        var car = carRepository.findByregistrationplate(registrationplate);
        person.getCars().remove(car);
    }

    @Override
    public ResponseEntity<?> changeUserStatus(String dni, boolean isEnabled) {
        var person = personRepository.findByDni(dni);
        if (person == null) throw new UserNotFoundException("No se encontró persona con el dni: " + dni);
        person.setEnabled(isEnabled);
        try {
            personRepository.save(person);
        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }
        var body = new HashMap<>();
        body.put("message", "Estado del conductor modificado con exito!");
        return ResponseEntity.ok(body);
    }

    @Override
    public Long countPerson() {
        return personRepository.count();
    }

    @Override
    public Optional<Person> searchPerson(String dni) {
        var checkPerson = personRepository.findById(dni);
        if (checkPerson.isPresent()) {
            return personRepository.findById(dni);
        } else {
            throw new UserNotFoundException("The dni: " + dni + " doesn't exists");
        }

    }



}
