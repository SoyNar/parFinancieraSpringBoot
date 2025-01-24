package par.financiera.financiera.Exceptions.ExceptionClass;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {

        super(message);
    }
}
