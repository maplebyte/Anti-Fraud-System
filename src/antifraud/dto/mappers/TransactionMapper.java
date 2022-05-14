package antifraud.dto.mappers;

import antifraud.dto.TransactionDTO;
import antifraud.entities.Transaction;
import antifraud.utils.TransactionStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TransactionMapper {

    public Transaction transactionDTOToTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getTransactionId());
        transaction.setNumber(transactionDTO.getNumber());
        transaction.setIp(transactionDTO.getIp());
        transaction.setWorldRegion(transactionDTO.getRegion());
        transaction.setDate(transactionDTO.getDate());
        transaction.setAmount(transactionDTO.getAmount());
        if (Objects.nonNull(transactionDTO.getResult())) {
            transaction.setStatus(TransactionStatus.valueOf(transactionDTO.getResult()));
        }
        if (Objects.nonNull(transactionDTO.getFeedback())) {
            transaction.setFeedback(transactionDTO.getFeedback());
        }
        return transaction;
    }

    public TransactionDTO transactionToTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(transaction.getId());
        transactionDTO.setNumber(transaction.getNumber());
        transactionDTO.setIp(transaction.getIp());
        transactionDTO.setRegion(transaction.getWorldRegion());
        transactionDTO.setDate(transaction.getDate());
        transactionDTO.setAmount(transaction.getAmount());
        if (Objects.nonNull(transaction.getStatus())) {
            transactionDTO.setResult(transaction.getStatus().name());
        }
        if (Objects.nonNull(transaction.getFeedback())) {
            transactionDTO.setFeedback(transaction.getFeedback());
        }
        return transactionDTO;
    }

}
