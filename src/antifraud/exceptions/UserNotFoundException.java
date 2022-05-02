package antifraud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("Exception: User with username " + username + " not found" );
    }

}
