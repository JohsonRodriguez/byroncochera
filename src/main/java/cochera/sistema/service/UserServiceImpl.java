package cochera.sistema.service;


import cochera.sistema.dto.UpdatePasswordDto;

//import cochera.sistema.entity.Role;
import cochera.sistema.exception.UserRepositoryException;
//import cochera.sistema.repository.RoleRepository;
import cochera.sistema.repository.RoleRepository;
import cochera.sistema.repository.UserRepository;
import cochera.sistema.security.entity.Rol;
import cochera.sistema.security.entity.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.findByNombreUsuario(username);
        if (user == null) {
            log.error("User not in the databse");
            throw new UsernameNotFoundException("User not in the databse");
        }else {
            log.error("User  in the databse");
        }
        Collection<SimpleGrantedAuthority>authorities=new ArrayList<>();
        user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getRolNombre()));});
        return new org.springframework.security.core.userdetails.User(user.getNombreUsuario(),user.getPassword(),authorities);
    }

    @Override
    public ResponseEntity<?> saveUser(Usuario user) {
        log.info("Saving a new user {}", user.getNombre());
        var employee = userRepository.findByNombreUsuario(user.getNombreUsuario());
        if (employee != null) throw new UserRepositoryException("Nombre de usuario ya registrado");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setState(true);
        Usuario newUser = null;
        try {
            newUser = userRepository.save(user);
        } catch (Exception e) {
            throw new UserRepositoryException(e.getMessage());
        }
        var body = new HashMap<>();
        body.put("message", "Usuario creado con exito!");
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<?> saveRole(Rol role) {
        log.info("Saving a new role {}", role.getRolNombre());
        var r = roleRepository.findByRolNombre(role.getRolNombre());
        if (r != null) throw new UserRepositoryException("Rol ya registrado");
        Rol newRole = null;
        try {
            newRole = roleRepository.save(role);
        } catch (Exception e) {
            throw new UserRepositoryException(e.getMessage());
        }
        var body = new HashMap<>();
        body.put("message", "Rol creado con exito!");
        return ResponseEntity.ok(body);
    }

    @Override
    public void removeRoleToUser(String nombreUsuario, String rolNombre) {
        log.info("Removing role {} to user {}", rolNombre, nombreUsuario);
        try {
            var employee = userRepository.findByNombreUsuario(nombreUsuario);
            var role = roleRepository.findByRolNombre(rolNombre);
            employee.getRoles().remove(role);
        } catch (Exception e) {
            throw new UserRepositoryException("Usuario o role no registrado: " + e.getMessage());
        }
    }

    @Override
    public void addRoleToUser(String nombreUsuario, String rolNombre) {
        log.info("Adding role {} to user {}", rolNombre, nombreUsuario);
        try {
            var employee = userRepository.findByNombreUsuario(nombreUsuario);
            var role = roleRepository.findByRolNombre(rolNombre);
            employee.getRoles().add(role);
        } catch (Exception e){
            throw new UserRepositoryException("Usuario o role no registrado: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getUser(String nombreUsuario) {
        log.info("Fetching user {}", nombreUsuario);
        try {
            var employee = userRepository.findByNombreUsuario(nombreUsuario);
            if (employee == null) throw new UserRepositoryException("Usuario no registrado");
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            throw new UserRepositoryException("Ocurrio un problema al buscar el usuario : " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<?> changePassword(UpdatePasswordDto updatePassword) {
        try {
            var user = userRepository.findByNombreUsuario(updatePassword.getNombreUsuario());
            user.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw  new UserRepositoryException("Error al actualizar el password del usuario: " + updatePassword.getNombreUsuario());
        }
        var body = new HashMap<>();
        body.put("message", "Password actualizado con exito!");
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<?> updateUser(Usuario user) {
        var employee = userRepository.findByNombreUsuario(user.getNombreUsuario());
        if (employee == null) throw new UserRepositoryException("No se encontró un usuario con el username: " + user.getNombreUsuario());
        employee.setNombre(user.getNombre());
        employee.setNombreUsuario(user.getNombreUsuario());
        try {
            userRepository.save(employee);
        } catch (Exception e) {
            throw new UserRepositoryException(e.getMessage());
        }
        var body = new HashMap<>();
        body.put("message", "Usuario actualizado con exito!");
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<?> changeUserStatus(String username, boolean isEnabled) {
        var employee = userRepository.findByNombreUsuario(username);
        if (employee == null) throw new UserRepositoryException("No se encontró un usuario con el username: " + username);
        employee.setState(isEnabled);
        try {
            userRepository.save(employee);
        } catch (Exception e) {
            throw new UserRepositoryException(e.getMessage());
        }
        var body = new HashMap<>();
        body.put("message", "Estado del usuario modificado con exito!");
        return ResponseEntity.ok(body);
    }


}
