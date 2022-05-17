package cochera.sistema.service;


import cochera.sistema.dto.UpdatePasswordDto;

import cochera.sistema.security.entity.Rol;
import cochera.sistema.security.entity.Usuario;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface UserService {
    ResponseEntity<?> saveUser(Usuario employee);
    ResponseEntity<?> saveRole (Rol role);
    void addRoleToUser(String nombreUsuario, String rolNombre);
    void removeRoleToUser(String nombreUsuario, String roleName);
    ResponseEntity<?> getUser(String username);
    List<Usuario> getUsers();
    ResponseEntity<?> updateUser(Usuario employee);
    ResponseEntity<?> changeUserStatus (String username, boolean isEnabled);
    ResponseEntity<?> changePassword(UpdatePasswordDto updatePassword);
}