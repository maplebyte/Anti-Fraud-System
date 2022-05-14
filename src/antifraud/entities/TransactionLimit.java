package antifraud.entities;

import antifraud.utils.TransactionStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "transaction_limits")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLimit {

    @Id
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private long maxAmount;

    private long minAmount;

}

