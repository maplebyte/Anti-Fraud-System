package antifraud.dto;

import antifraud.validation.patterns.PatternsValidatorUtil;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Data
public class SuspiciousIpDTO {

    private Long id;

    @NotEmpty(message = "IP mustn't be empty")
    @NotNull(message = "IP mustn't be null")
    @Pattern(regexp = PatternsValidatorUtil.IP_FORMAT, message = "Invalid IP format.")
    private String ip;

}
