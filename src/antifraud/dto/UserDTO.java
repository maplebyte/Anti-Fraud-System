package antifraud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Name mustn't be empty")
    @NotNull(message = "Name mustn't be null")
    @Size(max = 40, message = "Name name must be longer than 40 characters")
    private String name;

    @NotEmpty(message = "Username mustn't be empty")
    @NotNull(message = "Username mustn't be null")
    @Size(max = 40, message = "Username name must be longer than 40 characters")
    private String username;

    @NotEmpty
    @Size(min = 3, message = "Password must be longer than 40 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
