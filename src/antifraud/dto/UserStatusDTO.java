package antifraud.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserStatusDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;

    @NotEmpty(message = "User status mustn't be empty")
    @NotNull(message = "User status mustn't be null")
    private String status;

    private UserStatusDTO(String status) {
        this.status = status;
    }

    public static class UserLocked extends UserStatusDTO {
        public UserLocked(String username) {
            super(String.format("User %s locked!", username));
        }
    }

    public static class UserUnlocked extends UserStatusDTO {
        public UserUnlocked(String username) {
            super(String.format("User %s unlocked!", username));
        }
    }

}
