package antifraud.dto;

import lombok.Data;
import org.hibernate.validator.constraints.LuhnCheck;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class StolenCardDTO {

    private Long id;

    @NotEmpty
    @NotNull
    @LuhnCheck(message = "Invalid card number.")
    private String number;

}
