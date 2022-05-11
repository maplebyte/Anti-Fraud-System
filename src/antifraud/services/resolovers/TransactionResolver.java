package antifraud.services.resolovers;

import antifraud.dto.TransactionDTO;
import antifraud.dto.TransactionResultDTO;
import antifraud.entities.Transaction;
import antifraud.exceptions.TransactionException;
import antifraud.respositories.TransactionRepository;
import antifraud.services.StolenCardService;
import antifraud.services.SuspiciousIpService;
import antifraud.utils.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

@Component
public class TransactionResolver {

    private static final String ALL_IS_VALID = "none";
    private static final String CARD_IS_STOLEN = "card-number";
    private static final String IP_IS_BLOCKED = "ip";
    private static final String AMOUNT_IS_INVALID = "amount";
    private static final String SUSPICIOUS_IP = "ip-correlation";
    private static final String SUSPICIOUS_REGION = "region-correlation";

    private static final long TIME_BEFORE_TRANSACTION = 1;

    private final SuspiciousIpService suspiciousIpService;
    private final StolenCardService stolenCardService;
    private final TransactionRepository transactionRepository;

    private TransactionStatus currentTransactionStatus;

    @Autowired
    public TransactionResolver(SuspiciousIpService suspiciousIpService, StolenCardService stolenCardService, TransactionRepository transactionRepository) {
        this.suspiciousIpService = suspiciousIpService;
        this.stolenCardService = stolenCardService;
        this.transactionRepository = transactionRepository;
    }

    public TransactionResultDTO checkReason(TransactionDTO transaction) {
        Long amount = transaction.getAmount();
        currentTransactionStatus = getTransactionStatusByAmount(amount);
        Set<String> info = new TreeSet<>();

        if (currentTransactionStatus == TransactionStatus.ALLOWED) {
            info.add(ALL_IS_VALID);
        } else {
            info.add(AMOUNT_IS_INVALID);
        }

        if (suspiciousIpService.isInBlacklist(transaction.getIp())) {
           updateStatus(info, TransactionStatus.PROHIBITED);
            info.add(IP_IS_BLOCKED);
        }

        if (stolenCardService.isInBlacklist(transaction.getNumber())) {
            updateStatus(info, TransactionStatus.PROHIBITED);
            info.add(CARD_IS_STOLEN);
        }

        long checkRegion = getSuspiciousRegionsAmount(transaction);
        if (checkRegion > 2) {
            updateStatus(info, TransactionStatus.PROHIBITED);
            info.add(SUSPICIOUS_REGION);
        } else if (checkRegion == 2) {
            updateStatus(info, TransactionStatus.MANUAL_PROCESSING);
            info.add(SUSPICIOUS_REGION);
        }

        long checkIp = getSuspiciousIPsAmount(transaction);
        if (checkIp > 2) {
            updateStatus(info, TransactionStatus.PROHIBITED);
            info.add(SUSPICIOUS_IP);
        } else if (checkIp == 2) {
            updateStatus(info, TransactionStatus.MANUAL_PROCESSING);
            info.add(SUSPICIOUS_IP);
        }

        return new TransactionResultDTO(currentTransactionStatus, String.join(", ", info));
    }

    private void updateStatus(Set<String> info, TransactionStatus newTransactionStatus) {
        if (currentTransactionStatus != newTransactionStatus) {
            info.clear();
        }
        currentTransactionStatus = newTransactionStatus;
    }

    private long getSuspiciousIPsAmount(TransactionDTO transactionDTO) {
        return transactionRepository.findAllByNumberAndIpNotAndDateBetween(
                        transactionDTO.getNumber(),
                        transactionDTO.getIp(),
                        transactionDTO.getDate().minusHours(TIME_BEFORE_TRANSACTION),
                        transactionDTO.getDate()
                ).stream()
                .map(Transaction::getIp)
                .distinct()
                .count();
    }

    private long getSuspiciousRegionsAmount(TransactionDTO transactionDTO) {
        return transactionRepository.findAllByNumberAndWorldRegionNotAndDateBetween(
                        transactionDTO.getNumber(),
                        transactionDTO.getRegion(),
                        transactionDTO.getDate().minusHours(TIME_BEFORE_TRANSACTION),
                        transactionDTO.getDate()
                ).stream()
                .map(Transaction::getWorldRegion)
                .distinct()
                .count();
    }

    private TransactionStatus getTransactionStatusByAmount(Long amount) {
        return Arrays.stream(TransactionStatus.values())
                .filter(num -> Math.max(num.getMinAmount(), amount)
                        == Math.min(amount, num.getMaxAmount()))
                .findFirst()
                .orElseThrow(TransactionException::new);
    }

}
