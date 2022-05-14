package antifraud.dto;

import lombok.Data;
import org.hibernate.validator.constraints.LuhnCheck;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class StolenCardDTO {

    private Long id;

    @NotEmpty(message = "Number mustn't be empty")
    @NotNull(message = "Number mustn't be null")
    @LuhnCheck(message = "Invalid card number.")
    private String number;

}
