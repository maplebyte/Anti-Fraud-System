package antifraud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class SuspiciousIpAlreadyExistException extends RuntimeException {

    public SuspiciousIpAlreadyExistException(String ip) {
        super("Exception: IP  " + ip + " already exist" );
    }

}
