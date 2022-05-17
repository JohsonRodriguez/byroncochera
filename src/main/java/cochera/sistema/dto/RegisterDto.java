package cochera.sistema.dto;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
public class RegisterDto {

    @NotNull
    private String user;
    @NotNull
//    @Size(max = 8, min = 8)
    private String registrationplate;
    private String dni;
    private String observation;
}

