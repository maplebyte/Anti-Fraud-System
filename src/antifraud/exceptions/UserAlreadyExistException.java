package antifraud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String username) {
        super("Exception: User with username " + username + " already exist" );
    }

}
