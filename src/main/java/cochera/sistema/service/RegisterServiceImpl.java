package cochera.sistema.service;


import cochera.sistema.dto.RegisterDto;
import cochera.sistema.entity.Register;
import cochera.sistema.exception.UserNotFoundException;
import cochera.sistema.repository.CarRepository;
import cochera.sistema.repository.PersonRepository;
import cochera.sistema.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CarRepository carRepository;

    @Override
    public void saveRegister(RegisterDto registerDto) {
        var placa=registerDto.getRegistrationplate();

        var day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        var checkinHour = new SimpleDateFormat("HH:mm:ss").format(new Date());
        var register = new Register();
        var person = personRepository.findFirstByDni(registerDto.getDni());
        if (person!=null) {
            var car = carRepository.findByregistrationplate(registerDto.getRegistrationplate().toUpperCase());
            if (car!=null) {
                var registros = registerRepository.findAllByPersonAndDay(person, day);
                if (registros.size() > 0) {
                    var ultimoRegistro = registros.get(registros.size() - 1);
                    if (!ultimoRegistro.getCheckout().equals("pendiente")) {
                        register.setPerson(person);
                        register.setRegistrationplate(registerDto.getRegistrationplate().toUpperCase());
                        register.setCheckin(checkinHour);
                        register.setUser_checkin(registerDto.getUser());
                        register.setObs_checkin(registerDto.getObservation());
                        register.setCheckout("pendiente");
                        register.setDay(day);
                        registerRepository.save(register);
                    } else {
                        throw new UserNotFoundException("Vehiculo ya ingreso, registre su salida primero");
                    }
                }else{
                    register.setPerson(person);
                    register.setRegistrationplate(registerDto.getRegistrationplate().toUpperCase());
                    register.setCheckin(checkinHour);
                    register.setUser_checkin(registerDto.getUser());
                    register.setObs_checkin(registerDto.getObservation());
                    register.setCheckout("pendiente");
                    register.setDay(day);
                    registerRepository.save(register);
                }
            } else {
                throw new UserNotFoundException("Error en registro, vehiculo no existe");
            }

        }else {
            throw new UserNotFoundException("Conductor no existe, registre primero al conductor");
        }




    }

    @Override
    public void updateRegister(RegisterDto registerDto) {
        var car = carRepository.findByregistrationplate(registerDto.getRegistrationplate().toUpperCase());
        var placa= registerDto.getRegistrationplate().toUpperCase();

        if (car!=null) {
            var day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            var person = personRepository.findFirstByDni(registerDto.getDni());
            var registros = registerRepository.findAllByPersonAndDay(person, day);
            if (registros.size() > 0) {
                var ultimoRegistro = registros.get(registros.size() - 1);

                if (ultimoRegistro.getCheckout().equals("pendiente")) {
                    var placa2=ultimoRegistro.getRegistrationplate();
                    if (placa2.equals(placa)){
                        var checkoutHour = new SimpleDateFormat("HH:mm:ss").format(new Date());
                        ultimoRegistro.setCheckout(checkoutHour);
                        ultimoRegistro.setUser_checkout(registerDto.getUser());
                        ultimoRegistro.setObs_checkout(registerDto.getObservation());
                        registerRepository.save(ultimoRegistro);
                    }else{
                        throw new UserNotFoundException("Placa de Vehiculo no concuerda con la placa de ingreso: " + placa2);
                    }

                } else {
                    throw new UserNotFoundException("Vehiculo ya registro salida");
                }
            } else {
                throw new UserNotFoundException("Vehiculo aun no ha ingresado");
            }
        } else {
            throw new UserNotFoundException("Vehiculo dosen't exists");
        }


    }


    @Override
    public List<Register> searchDay(String day) {
        return registerRepository.findAllByDay(day);
    }

    @Override
    public Optional<Register> searchById(Long id) {
        return registerRepository.findById(id);
    }

    @Override
    public Long countRegistro() {
        return registerRepository.count();
    }

    @Override
    public Long countGaragefree() {
        var today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//         var checkin=  registerRepository.countByDay(today);
        var checkout=registerRepository.countCheckout(today,"pendiente");
        var free= 100 - (checkout);
        return free;

    }

    @Override
    public List<Register> searchCarbyDay (String registrationplate) {
//        var car = carRepository.findByRegistrationplate(registrationplate);
//        if (car.isPresent()) {
//            var day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//            return registerRepository.findAllByCarAndDay(car.get(), day);
//        }else {
//             throw new UserNotFoundException("Person doesn't exists");
//        }
        return null;
    }

}

