package cochera.sistema.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "register")

public class Register implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String registrationplate;
    private String day;
    private String checkin;
    private String obs_checkin;
    private String user_checkin;
    private String checkout;
    private String obs_checkout;
    private String user_checkout;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_dni")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Person person;


}

