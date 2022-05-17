package cochera.sistema.service;



import cochera.sistema.dto.RegisterDto;
import cochera.sistema.entity.Register;


import java.util.List;
import java.util.Optional;

public interface RegisterService {

    void  saveRegister(RegisterDto registerDto);
    List<Register>searchCarbyDay(String registrationplate);
    void  updateRegister(RegisterDto registerDto);
    List<Register> searchDay(String day);
    Optional<Register> searchById(Long id);
    Long countRegistro();
    Long countGaragefree();

}

