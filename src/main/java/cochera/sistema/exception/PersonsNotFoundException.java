package cochera.sistema.exception;

public class PersonsNotFoundException extends RuntimeException{
    public PersonsNotFoundException(String message){
        super(message);
    }
}
