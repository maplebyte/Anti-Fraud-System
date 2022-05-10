package antifraud.services;

import antifraud.dto.TransactionResultDTO;
import antifraud.dto.TransactionDTO;
import antifraud.exceptions.TransactionException;
import antifraud.services.resolovers.TransactionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransactionService {

   private final TransactionResolver transactionResolver;

   @Autowired
    public TransactionService(TransactionResolver transactionResolver) {
        this.transactionResolver = transactionResolver;
    }

    public TransactionResultDTO validate(TransactionDTO transaction) {
        if (Objects.isNull(transaction)) {
            throw new TransactionException();
        }
        if (Objects.isNull(transaction.getAmount())) {
            throw new TransactionException();
        }
        return transactionResolver.checkReason(transaction);
    }

}
