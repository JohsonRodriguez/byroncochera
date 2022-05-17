package cochera.sistema.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdatePasswordDto {
    @NotBlank
    private String nombreUsuario;
    @NotBlank
    private String newPassword;
}
