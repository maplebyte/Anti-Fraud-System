package antifraud.dto;

import antifraud.utils.Role;
import antifraud.utils.WorldRegion;
import antifraud.validation.enums.ValidEnum;
import antifraud.validation.patterns.PatternsValidatorUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.LuhnCheck;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {

    private Long id;

    private Long amount;

    @NotNull
    @NotEmpty
    @Pattern(regexp = PatternsValidatorUtil.IP_FORMAT, message = "Invalid IP format.")
    private String ip;

    @LuhnCheck(message = "Invalid card number.")
    private String number;

    @ValidEnum(clazz = WorldRegion.class, message = "World region is invalid.")
    private String region;

    private LocalDateTime date;

}
