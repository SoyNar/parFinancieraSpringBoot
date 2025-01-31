package par.financiera.financiera.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import par.financiera.financiera.Exceptions.ExceptionClass.CashFlowNotFound;
import par.financiera.financiera.Exceptions.ExceptionClass.InvalidRequestException;
import par.financiera.financiera.Exceptions.ExceptionClass.ModelNotFounExcceptions;
import par.financiera.financiera.Exceptions.ExceptionClass.UserNotFoundException;

@ControllerAdvice
public class GlobalHandlerExceptions {

    //lanzadores

    //lanzador de usuario no encontrado
   @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFoundException(UserNotFoundException exception){
        ErrorResponse errors = ErrorResponse.builder()
                .error("User not found")
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFoundException(InvalidRequestException exception){
        ErrorResponse errors = ErrorResponse.builder()
                .error("BAD REQUEST")
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ModelNotFounExcceptions.class)
    public ResponseEntity<ErrorResponse> handlerModelNotFoundException(ModelNotFounExcceptions exception){
        ErrorResponse errors = ErrorResponse.builder()
                .error("NO ENCONTRADO")
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CashFlowNotFound.class)
    public ResponseEntity<ErrorResponse> handlerModelNotFoundException(CashFlowNotFound exception){
        ErrorResponse errors = ErrorResponse.builder()
                .error("CASH_NOT_FOUND")
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
