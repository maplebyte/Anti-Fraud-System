package antifraud.dto;

import antifraud.validation.role.ValidRole;
import lombok.Data;

@Data
public class UserRoleDTO {

    @ValidRole(message = "Role is invalid.")
    private String role;

    private String username;

}
