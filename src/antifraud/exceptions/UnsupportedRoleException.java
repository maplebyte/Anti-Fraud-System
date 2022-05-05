package antifraud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UnsupportedRoleException extends RuntimeException {

    public UnsupportedRoleException(String message) {
        super(message);
    }

}


