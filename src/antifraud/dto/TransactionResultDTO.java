package antifraud.dto;

import antifraud.utils.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionResultDTO {

    @Enumerated(EnumType.STRING)
    private TransactionStatus result;

    @NotEmpty(message = "Transaction information mustn't be empty")
    @NotNull(message = "Transaction information  mustn't be null")
    private String info;

}
