package cochera.sistema.repository;



import cochera.sistema.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreUsuario(String username);
}

