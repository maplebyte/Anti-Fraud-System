package antifraud.services;

import antifraud.dto.ResultDTO;
import antifraud.dto.TransactionDTO;
import antifraud.exceptions.TransactionException;
import antifraud.utils.TransactionStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
public class TransactionService {

    public ResultDTO validate(TransactionDTO transaction) {
        if (Objects.isNull(transaction)) {
            throw new TransactionException();
        }
        if (Objects.isNull(transaction.getAmount())) {
            throw new TransactionException();
        }
        Long amount = transaction.getAmount();

        TransactionStatus transactionStatus = Arrays
                .stream(TransactionStatus.values())
                .filter(num -> Math.max(num.getMinAmount(), amount) == Math.min(amount, num.getMaxAmount()))
                .findFirst()
                .orElseThrow(TransactionException::new);

        return new ResultDTO(transactionStatus.name());
    }

}
