package antifraud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserStatusDTO {

    @NotEmpty(message = "Username mustn't be empty")
    @NotNull(message = "Username mustn't be null")
    private String username;

    @NotEmpty(message = "User status mustn't be empty")
    @NotNull(message = "User status mustn't be null")
    private String status;

}
