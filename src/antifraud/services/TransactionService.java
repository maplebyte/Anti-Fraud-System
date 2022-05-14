package antifraud.services;

import antifraud.dto.FeedBackDTO;
import antifraud.dto.TransactionDTO;
import antifraud.dto.TransactionResultDTO;
import antifraud.dto.mappers.TransactionMapper;
import antifraud.entities.Transaction;
import antifraud.exceptions.EntityAlreadyExistException;
import antifraud.exceptions.EntityNotFoundException;
import antifraud.exceptions.SameFeedbackAndStatusValueException;
import antifraud.exceptions.TransactionException;
import antifraud.respositories.TransactionRepository;
import antifraud.services.resolovers.TransactionResolver;
import antifraud.services.resolovers.FeedbackResolver;
import antifraud.utils.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionService {

    private final TransactionResolver transactionResolver;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final FeedbackResolver feedbackResolver;

    @Autowired
    public TransactionService(TransactionResolver transactionResolver, TransactionRepository transactionRepository, TransactionMapper transactionMapper, FeedbackResolver feedbackResolver) {
        this.transactionResolver = transactionResolver;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.feedbackResolver = feedbackResolver;
    }

    public TransactionResultDTO validate(TransactionDTO transactionDTO) {
        if (Objects.isNull(transactionDTO)) {
            throw new TransactionException();
        }
        if (Objects.isNull(transactionDTO.getAmount())) {
            throw new TransactionException();
        }
        TransactionResultDTO transactionResultDTO = transactionResolver.checkReason(transactionDTO);
        transactionDTO.setResult(transactionResultDTO.getResult().name());
        transactionDTO.setFeedback("");
        Transaction savedTransaction = transactionMapper.transactionDTOToTransaction(transactionDTO);
        transactionRepository.save(savedTransaction);
        log.info("Saved transaction {}", savedTransaction);
        return transactionResultDTO;
    }

    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAllByOrderByIdAsc();
        return transactionListToTransactionDTOList(transactions);
    }

    public List<TransactionDTO> getTransactionsByCardNumber(String number) {
        if(!transactionRepository.existsByNumber(number)) {
            throw new EntityNotFoundException(number);
        }
        List<Transaction> transactions = transactionRepository.findAllByNumber(number);
        return transactionListToTransactionDTOList(transactions);
    }

    private List<TransactionDTO> transactionListToTransactionDTOList(List<Transaction> transactions) {
        return transactions
                .stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO changeFraudLimits(FeedBackDTO feedBackDTO) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(feedBackDTO.getTransactionId());
        if (transactionOptional.isEmpty()) {
            throw new EntityNotFoundException(feedBackDTO.getTransactionId().toString());
        }
        Transaction transaction = transactionOptional.get();

        TransactionStatus feedback = TransactionStatus.valueOf(feedBackDTO.getFeedback()); //Transaction Feedback
        TransactionStatus status = transaction.getStatus(); // Transaction Validity
        log.info(transaction.toString());

        if(feedback == status) {
            throw new SameFeedbackAndStatusValueException();
        }

        if(!transaction.getFeedback().isEmpty()) {
            throw new EntityAlreadyExistException();
        }

        transaction.setFeedback(feedback.name());
        transactionRepository.save(transaction);

        feedbackResolver.updateLimit(feedback, status, transaction.getAmount());

        log.info("Updated transaction {} ", transaction);
        return transactionMapper.transactionToTransactionDTO(transaction);
    }

}

