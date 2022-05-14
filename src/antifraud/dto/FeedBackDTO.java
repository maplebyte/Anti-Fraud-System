package antifraud.dto;

import antifraud.utils.TransactionStatus;
import antifraud.validation.enums.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedBackDTO {

    private Long transactionId;

    @NotNull(message = "Feedback mustn't be null")
    @ValidEnum(clazz = TransactionStatus.class, message = "Transaction feedback is invalid.")
    private String feedback;

}
