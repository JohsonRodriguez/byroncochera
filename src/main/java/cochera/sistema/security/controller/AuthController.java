package cochera.sistema.security.controller;

import cochera.sistema.dto.Mensaje;
import cochera.sistema.dto.RoleToUserDto;
import cochera.sistema.dto.UpdatePasswordDto;
import cochera.sistema.exception.UserRepositoryException;
import cochera.sistema.security.dto.JwtDto;
import cochera.sistema.security.dto.LoginUsuario;
import cochera.sistema.security.dto.NuevoUsuario;
import cochera.sistema.security.entity.Rol;
import cochera.sistema.security.entity.Usuario;
import cochera.sistema.security.entity.UsuarioPrincipal;
import cochera.sistema.security.enums.RolNombres;
import cochera.sistema.security.jwt.JwtProvider;
import cochera.sistema.security.service.RolService;
import cochera.sistema.security.service.UsuarioService;
import cochera.sistema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/nuevo")
//    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
//        if(bindingResult.hasErrors())
//            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
//        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
//            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
////        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
////            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
//       Usuario usuario =
//                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(),
//                        passwordEncoder.encode(nuevoUsuario.getPassword()), nuevoUsuario.isEnabled());
//        Set<Rol> roles = new HashSet<>();
//        roles.add(rolService.getByRolNombre(RolNombres.ROLE_USER).get());
//        if(nuevoUsuario.getRoles().contains("admin"))
//            roles.add(rolService.getByRolNombre(RolNombres.ROLE_ADMIN).get());
//        usuario.setRoles(roles);
//        usuarioService.save(usuario);
//        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
//    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            Boolean state=  ((UsuarioPrincipal)authentication.getPrincipal()).getState();
            String name=((UsuarioPrincipal)authentication.getPrincipal()).getNombre();
            if(state.equals(true)){
                UserDetails userDetails = (UserDetails)authentication.getPrincipal();
                JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), name,userDetails.getAuthorities());
                return new ResponseEntity(jwtDto, HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("Usuario Bloqueado"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Usuario y contraseña no son correctos"), HttpStatus.BAD_REQUEST);
        }



    }

    private final UserService userService;

    @GetMapping("/users/list")
    public ResponseEntity<List<Usuario>> getUsers () {
        return ResponseEntity.ok().body(userService.getUsers());
    }

        @PostMapping("/users")
    public ResponseEntity<?> saveUser (@Valid @RequestBody Usuario employee) {
        return userService.saveUser(employee);
    }

        @PostMapping("/roles/add-to-user")
    public ResponseEntity<?> addRoleToUser (@Valid @RequestBody RoleToUserDto roleToUserDto) {
        userService.addRoleToUser(roleToUserDto.getNombreUsuario(), roleToUserDto.getRolNombre());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUserByUserName(@RequestParam String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.isBlank()) throw new UserRepositoryException("EL nombre de usuario es obligatorio!");
        return userService.getUser(nombreUsuario);
    }

        @PutMapping("/users/update")
    public ResponseEntity<?> updateUser (@RequestBody Usuario employee) {
        return userService.updateUser(employee);
    }

        @PostMapping("/users/change-password")
    public ResponseEntity<?> updateUser (@Valid @RequestBody UpdatePasswordDto updatePassword) {
        return userService.changePassword(updatePassword);
    }

        @PostMapping("/roles/remove-from-user")
    public ResponseEntity<?> removeRoleFromUser (@Valid @RequestBody RoleToUserDto roleToUserDto) {
        userService.removeRoleToUser(roleToUserDto.getNombreUsuario(), roleToUserDto.getRolNombre());
        return ResponseEntity.ok().build();
    }

        @PutMapping("/users/status")
    public ResponseEntity<?> changeUserStatus(@RequestParam String username,
                                              @RequestParam boolean isEnabled) {
        if (username == null || username.isBlank()) throw new UserRepositoryException("El nombre de usuario es obligatorio!");
        return userService.changeUserStatus(username, isEnabled);
    }

}
