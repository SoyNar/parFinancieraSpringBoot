package par.financiera.financiera.Exceptions.ExceptionClass;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
