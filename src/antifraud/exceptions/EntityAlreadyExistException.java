package antifraud.exceptions;

import antifraud.utils.Role;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String username) {
        super("Exception: Entity with data " + username + " already exist" );
    }

    public EntityAlreadyExistException(Role role) {
        super(role.name() + " is already provided");
    }

}


