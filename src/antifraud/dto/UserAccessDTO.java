package antifraud.dto.userDTOs;

import antifraud.utils.Operation;
import antifraud.validation.ValidEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserAccessDTO {

    @ValidEnum(clazz = Operation.class, message = "Operation is invalid.")
    private String operation;

    @NotEmpty(message = "Username mustn't be empty")
    @NotNull(message = "Username mustn't be null")
    private String username;

}

