package antifraud.dto.mappers;

import antifraud.dto.TransactionDTO;
import antifraud.entities.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction transactionDTOToTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getId());
        transaction.setNumber(transactionDTO.getNumber());
        transaction.setIp(transactionDTO.getIp());
        transaction.setWorldRegion(transactionDTO.getRegion());
        transaction.setDate(transactionDTO.getDate());
        return transaction;
    }

    public TransactionDTO transactionToTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setNumber(transaction.getNumber());
        transactionDTO.setIp(transaction.getIp());
        transactionDTO.setRegion(transaction.getWorldRegion());
        transactionDTO.setDate(transaction.getDate());
        return transactionDTO;
    }

}
