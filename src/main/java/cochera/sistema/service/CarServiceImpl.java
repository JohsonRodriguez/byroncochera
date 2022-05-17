package cochera.sistema.service;



import cochera.sistema.entity.Car;
import cochera.sistema.exception.UserNotFoundException;
import cochera.sistema.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService{



    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Iterable<Car> listCar() {
        return carRepository.findAll();
    }

    @Override
    public void newCar(Car car) {
        var checkCarr= carRepository.findById(car.getRegistrationplate());
        if(!checkCarr.isPresent()){
            carRepository.save(car);
        }else{
            throw new UserNotFoundException("Car exists");
        }
    }

    @Override
    public void updateCar(Car car) {
        var vehicle=carRepository.findByregistrationplate(car.getRegistrationplate());
        if( vehicle!=null){
            carRepository.save(car);
        }else {
            throw new UserNotFoundException("Car doesn't exists");
        }

    }

    @Override
    public Long countCar() {
        return carRepository.count();
    }

    @Override
    public Car searchCar(String plate) {

        String Upperplate = plate.toUpperCase();
        var checkCar = carRepository.findByregistrationplate(Upperplate);
        if (checkCar != null) {

            return carRepository.findByregistrationplate(Upperplate);
        } else {
            throw new UserNotFoundException("La placa " + Upperplate + " no esta registrada");

        }

    }
    public Car findCarByRegistrationPlate(String registrationplate) {
        return carRepository.findByregistrationplate(registrationplate);
    }
}
