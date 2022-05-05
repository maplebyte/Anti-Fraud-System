package antifraud.dto;

import antifraud.utils.Operation;
import antifraud.validation.ValidEnum;
import lombok.Data;

@Data
public class UserAccessDTO {

    @ValidEnum(clazz = Operation.class, message = "Operation is invalid.")
    private String operation;

    private String username;

}

