package ccochera.sistema.controller;


import cochera.sistema.dto.RoleToUserDto;
import cochera.sistema.dto.UpdatePasswordDto;
//
//import cochera.sistema.entity.Role;
import cochera.sistema.exception.UserRepositoryException;
import cochera.sistema.security.entity.Usuario;
//import cochera.sistema.security.service.UsuarioService;

import cochera.sistema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/list")
    public ResponseEntity<List<Usuario>> getUsers () {
        return ResponseEntity.ok().body(userService.getUsers());
    }

//    @PostMapping("/users")
//    public ResponseEntity<?> saveUser (@Valid @RequestBody Usuario employee) {
//        return userService.saveUser(employee);
//    }
//
//    @PostMapping("/roles")
//    public ResponseEntity<?> saveRole (@Valid @RequestBody Role role) {
//        return userService.saveRole(role);
//    }
//

//

//

//

//

//


}