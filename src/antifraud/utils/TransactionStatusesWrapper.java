package antifraud.utils;

import antifraud.entities.TransactionLimit;
import antifraud.respositories.TransactionLimitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionStatusesWrapper {

    private final TransactionLimitRepository transactionLimitRepository;

    private static final TransactionStatus ALLOWED = TransactionStatus.ALLOWED;
    private static final TransactionStatus MANUAL_PROCESSING = TransactionStatus.MANUAL_PROCESSING;
    private static final TransactionStatus PROHIBITED = TransactionStatus.PROHIBITED;

    private final Map<TransactionStatus, Long> maxLimits = new HashMap<>();
    private final Map<TransactionStatus, Long> minLimits = new HashMap<>();

    @Autowired
    public TransactionStatusesWrapper(TransactionLimitRepository transactionLimitRepository) {
        this.transactionLimitRepository = transactionLimitRepository;

        maxLimits.put(ALLOWED, ALLOWED.getMaxAmount());
        maxLimits.put(MANUAL_PROCESSING, MANUAL_PROCESSING.getMaxAmount());
        maxLimits.put(PROHIBITED, PROHIBITED.getMaxAmount());

        minLimits.put(ALLOWED, ALLOWED.getMinAmount());
        minLimits.put(MANUAL_PROCESSING, MANUAL_PROCESSING.getMinAmount());
        minLimits.put(PROHIBITED, PROHIBITED.getMinAmount());
    }

    public void changeMaxLimitByTransactionStatus(TransactionStatus transactionStatus, long newValue) {
        if(transactionLimitRepository.existsByTransactionStatus(transactionStatus)) {
           TransactionLimit limit = transactionLimitRepository.findById(transactionStatus).get();
           maxLimits.computeIfPresent(transactionStatus, (key, oldValue) -> newValue);
           limit.setMaxAmount(newValue);
           transactionLimitRepository.save(limit);
        } else {
            TransactionLimit transactionLimit = new TransactionLimit();
            transactionLimit.setTransactionStatus(transactionStatus);
            maxLimits.computeIfPresent(transactionStatus, (key, oldValue) -> newValue);
            transactionLimit.setMaxAmount(newValue);
            transactionLimitRepository.save(transactionLimit);
        }
    }

    public void changeMinLimitByTransactionStatus(TransactionStatus transactionStatus, long newValue) {
        if(transactionLimitRepository.existsByTransactionStatus(transactionStatus)) {
            TransactionLimit limit = transactionLimitRepository.findById(transactionStatus).get();
            minLimits.computeIfPresent(transactionStatus, (key, oldValue) -> newValue);
            limit.setMinAmount(newValue);
            transactionLimitRepository.save(limit);
        } else {
            TransactionLimit transactionLimit = new TransactionLimit();
            transactionLimit.setTransactionStatus(transactionStatus);
            minLimits.computeIfPresent(transactionStatus, (key, oldValue) -> newValue);
            transactionLimit.setMinAmount(newValue);
            transactionLimitRepository.save(transactionLimit);
        }
    }

    public long getMaxAmountByTransactionStatus(TransactionStatus transactionStatus) {
        if(transactionLimitRepository.existsByTransactionStatus(transactionStatus)) {
            long max = transactionLimitRepository.findById(transactionStatus).get().getMaxAmount();
            if(max != 0) {
                return max;
            }
        }
        return maxLimits.get(transactionStatus);
    }

    public long getMinAmountByTransactionStatus(TransactionStatus transactionStatus) {
        if(transactionLimitRepository.existsByTransactionStatus(transactionStatus)) {
            long min = transactionLimitRepository.findById(transactionStatus).get().getMinAmount();
            if(min != 0) {
                return min;
            }
        }
        return minLimits.get(transactionStatus);
    }

}
