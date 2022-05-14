package antifraud.dto;

import antifraud.utils.TransactionStatus;
import antifraud.validation.enums.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedBackDTO {

    private Long transactionId;

    @ValidEnum(clazz = TransactionStatus.class, message = "Transaction feedback is invalid.")
    private String feedback;

}
