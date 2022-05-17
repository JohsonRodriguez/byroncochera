package cochera.sistema.repository;


import cochera.sistema.security.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Rol, Long> {
    Rol findByRolNombre(String rolNombre);
}
