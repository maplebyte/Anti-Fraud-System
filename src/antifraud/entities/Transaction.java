package antifraud.entities;

import antifraud.utils.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_history")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @NotBlank
    private String ip;

    @NotBlank
    private String number;

    @NotBlank
    private String worldRegion;

    @NotNull
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String feedback;

}
