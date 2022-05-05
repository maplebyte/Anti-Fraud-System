package antifraud.dto;

import antifraud.validation.operation.ValidOperation;
import lombok.Data;

@Data
public class UserAccessDTO {

    private String username;

    @ValidOperation(message = "Operation is invalid.")
    private String operation;

}

