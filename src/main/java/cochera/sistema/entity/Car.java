package cochera.sistema.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {
    @Id
    private String registrationplate;
    private String carbrand;
    private String carmodel;
    private String carcolor;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "car_person",
//            joinColumns = @JoinColumn(name = "car_id"),
//            inverseJoinColumns = @JoinColumn(name = "person_id")
//    )
//    @JsonIgnore
//    private Collection<Person> persons = new ArrayList<>();


}

