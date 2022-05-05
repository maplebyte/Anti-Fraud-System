package antifraud.dto;

import antifraud.utils.Role;
import antifraud.validation.ValidEnum;
import lombok.Data;

@Data
public class UserRoleDTO {

    @ValidEnum(clazz = Role.class, message = "Role is invalid.")
    private String role;

    private String username;

}
