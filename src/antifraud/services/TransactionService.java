package antifraud.services;

import antifraud.dto.TransactionDTO;
import antifraud.dto.TransactionResultDTO;
import antifraud.dto.mappers.TransactionMapper;
import antifraud.exceptions.TransactionException;
import antifraud.respositories.TransactionRepository;
import antifraud.services.resolovers.TransactionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class TransactionService {

    private final TransactionResolver transactionResolver;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionService(TransactionResolver transactionResolver, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionResolver = transactionResolver;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResultDTO validate(TransactionDTO transactionDTO) {
        if (Objects.isNull(transactionDTO)) {
            throw new TransactionException();
        }
        if (Objects.isNull(transactionDTO.getAmount())) {
            throw new TransactionException();
        }
        log.info("Incoming transaction {}", transactionDTO);
        transactionRepository.save(transactionMapper.transactionDTOToTransaction(transactionDTO));
        return transactionResolver.checkReason(transactionDTO);
    }

}
