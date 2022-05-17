package cochera.sistema.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleToUserDto {

    @NotBlank(message = "Username is mandatory")
    private String nombreUsuario;
    @NotBlank(message = "Role name is mandatory")
    private String rolNombre;
}