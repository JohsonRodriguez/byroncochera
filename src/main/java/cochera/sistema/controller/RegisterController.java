package cochera.sistema.controller;

import cochera.sistema.dto.RegisterDto;
import cochera.sistema.entity.Register;
import cochera.sistema.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveRegister(@Valid @RequestBody RegisterDto registerDto) {
        registerService.saveRegister(registerDto);
        return ResponseEntity.ok(Map.of("mensaje","Registro de ingreso Exitoso"));
    }

    @PostMapping("/checkout")
    public ResponseEntity<Map<String,String>>  updateRegister(@Valid @RequestBody RegisterDto registerDto){
        registerService.updateRegister(registerDto);
        return ResponseEntity.ok(Map.of("mensaje","Registro de salida exitoso"));
    }
    @GetMapping("find/{id}")
    public Optional<Register> getById(@PathVariable("id") Long id) {
        return registerService.searchById(id);
    }

    @GetMapping("/searchday/{day}")
    public List<Register>registerDiary(@PathVariable("day") String day){
        return registerService.searchDay(day);
    }

    @GetMapping("/count")
    public Long countRegister(){
        return registerService.countRegistro();
    }

    @GetMapping("/free")
    public Long countFree(){
        return registerService.countGaragefree();
    }

    @GetMapping("/searchplate/{registrationplate}")
    public List<Register>searchRegistersPerson(@PathVariable("registrationplate") String registrationplate){
        return registerService.searchCarbyDay(registrationplate);
    }
}

