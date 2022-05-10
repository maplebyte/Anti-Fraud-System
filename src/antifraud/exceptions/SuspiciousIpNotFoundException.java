package antifraud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SuspiciousIpNotFoundException extends RuntimeException {

    public SuspiciousIpNotFoundException(String ip) {
        super("Exception: IP  " + ip + " not found" );
    }

}
