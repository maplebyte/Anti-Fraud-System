package antifraud.exceptions;

import antifraud.utils.Role;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class RoleIsAlreadyProvidedException extends RuntimeException {

    public RoleIsAlreadyProvidedException(Role role) {
        super(role.name() + " is already provided");
    }

}
