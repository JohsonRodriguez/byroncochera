package cochera.sistema.repository;



import cochera.sistema.entity.Person;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PersonRepository extends CrudRepository<Person, String> {
    Person findFirstByDni(String dni);
    Person findByDni(String dni);



    //    @Query("SELECT p.dni, p.name, p.lastname, p.phone, p.type, p.state FROM  Person p")
    @Query("SELECT p FROM  Person AS p")
    Iterable<Person> AllPerson();

//    @Modifying
//    @Transactional
//    @Query("update Person p set p.name = :name, p.lastname = :lastname, p.phone = :phone, p.type = :type, p.state = :state where p.dni = :dni")
//    void  updatePerson(@Param(value = "name")String name,
//                       @Param(value = "lastname")String lastname,
//                       @Param(value = "phone")String phone,
//                       @Param(value = "type")String type,
//                       @Param(value = "state")String state,
//                       @Param(value = "dni")String dni);

//    @Mofying
////    @Transactional
////    @Query(value = "SELECT registrationplate, carbrand, carmodel, carcolor   FROM car LEFT JOIN car_person ON car_person.car_id = car.registrationplate where person_id=:person_id",nativeQuery = true)
////    void searchCars(String person_id);

}
