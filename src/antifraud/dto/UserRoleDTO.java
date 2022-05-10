package antifraud.dto.userDTOs;

import antifraud.utils.Role;
import antifraud.validation.ValidEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserRoleDTO {

    @ValidEnum(clazz = Role.class, message = "Role is invalid.")
    private String role;

    @NotEmpty(message = "Username mustn't be empty")
    @NotNull(message = "Username mustn't be null")
    private String username;

}
