package cochera.sistema.service;


import cochera.sistema.entity.Car;

public interface CarService {
    Iterable<Car> listCar();
    void newCar(Car car);
    void updateCar(Car car);
    Long countCar();
    Car searchCar(String plate);
}
