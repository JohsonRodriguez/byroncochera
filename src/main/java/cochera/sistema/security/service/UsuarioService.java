package cochera.sistema.security.service;

import cochera.sistema.dto.UpdatePasswordDto;
import cochera.sistema.security.entity.Rol;
import cochera.sistema.security.entity.Usuario;
import cochera.sistema.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

//    public boolean existsByEmail(String email){
//        return usuarioRepository.existsByEmail(email);
//    }

    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    //        ResponseEntity<?> saveUser(Usuario employee);
//    ResponseEntity<?> saveRole (Rol role);
//    void addRoleToUser(String username, String roleName);
//    void removeRoleToUser(String username, String roleName);
//    ResponseEntity<?> getUser(String username);
    List<Usuario> getUsers() {
        return null;
    }
//    ResponseEntity<?> updateUser(Usuario employee);
//    ResponseEntity<?> changeUserStatus (String username, boolean isEnabled);
//    ResponseEntity<?> changePassword(UpdatePasswordDto updatePassword);
}
