package cochera.sistema.controller;

import cochera.sistema.entity.Car;
import cochera.sistema.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    //inyectar x Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/all")
    public Iterable<Car> getAllCars(){
        return carService.listCar();
    }

    @PostMapping("/add")
    public void addCar(@RequestBody Car car){
        carService.newCar(car);
    }

    @PutMapping("/update")
    public void updateCar(@RequestBody Car car){
        carService.updateCar(car);
    }

    @GetMapping("/count")
    public Long countCar(){
        return carService.countCar();
    }

    @GetMapping("findcar/{plate}")
    public Car getByCar(@PathVariable("plate") String plate) {
        return carService.searchCar(plate);
    }



}

