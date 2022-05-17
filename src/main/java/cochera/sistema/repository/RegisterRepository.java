package cochera.sistema.repository;



import cochera.sistema.entity.Person;
import cochera.sistema.entity.Register;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface RegisterRepository extends CrudRepository<Register,Long> {

    List<Register>  findAllByPersonAndDay(Person person, String day);
    List<Register> findAllByDay (String day);
//    Long countByDay (String day);

    @Query("SELECT COUNT(r) FROM Register r WHERE r.day=:today AND r.checkout=:estate")
    long countCheckout(String today , String estate);

//        @Modifying
//    @Transactional
//    @Query(value = "SELECT COUNT (checkin) FROM register r where r.day='2022-01-10'",nativeQuery = true)
//        @Query(value = "SELECT * FROM register r where r.day=:today",nativeQuery = true)
//    List<R> countCheckin();

}
