package antifraud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserNullPointerException extends RuntimeException {

    public UserNullPointerException() {
        super("Exception: User is null");
    }

}
