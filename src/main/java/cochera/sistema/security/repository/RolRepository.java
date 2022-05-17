package cochera.sistema.security.repository;

import cochera.sistema.security.entity.Rol;
import cochera.sistema.security.enums.RolNombres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombres rolNombre);
}
