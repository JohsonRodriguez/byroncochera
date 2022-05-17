package cochera.sistema.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person  implements Serializable {
    @Id
    private String dni;
    private String name;
    private String lastname;
    private String phone;
    private String type;
    private boolean isEnabled;
//    @ManyToMany(mappedBy = "persons", cascade = CascadeType.ALL)
//    private Collection<Car> cars = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Car> cars = new ArrayList<>();



}
