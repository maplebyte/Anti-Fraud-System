package antifraud.services.resolovers;

import antifraud.dto.TransactionDTO;
import antifraud.dto.TransactionResultDTO;
import antifraud.exceptions.TransactionException;
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

    private static final String ALL_IS_CORRECT = "none";
    private static final String CARD_IS_STOLEN = "card-number";
    private static final String IP_IS_BLOCKED = "ip";
    private static final String AMOUNT_IS_INVALID = "amount";

    private final SuspiciousIpService suspiciousIpService;
    private final StolenCardService stolenCardService;

    @Autowired
    public TransactionResolver(SuspiciousIpService suspiciousIpService, StolenCardService stolenCardService) {
        this.suspiciousIpService = suspiciousIpService;
        this.stolenCardService = stolenCardService;
    }

    public TransactionResultDTO checkReason(TransactionDTO transaction) {
        Long amount = transaction.getAmount();
        TransactionStatus currentTransactionStatus = getTransactionStatusByAmount(amount);

        Set<String> info = new TreeSet<>();

        if (currentTransactionStatus == TransactionStatus.ALLOWED) {
            info.add(ALL_IS_CORRECT);
        } else {
            info.add(AMOUNT_IS_INVALID);
        }
        TransactionStatus newTransactionStatus;

        if (suspiciousIpService.isInBlacklist(transaction.getIp())) {
            newTransactionStatus = TransactionStatus.PROHIBITED;
            checkEqualsTransactionStatus(info, currentTransactionStatus, newTransactionStatus);
            info.add(IP_IS_BLOCKED);
            currentTransactionStatus = newTransactionStatus;
        }

        if(stolenCardService.isInBlacklist(transaction.getNumber())) {
            newTransactionStatus = TransactionStatus.PROHIBITED;
            checkEqualsTransactionStatus(info, currentTransactionStatus, newTransactionStatus);
            info.add(CARD_IS_STOLEN);
            currentTransactionStatus = newTransactionStatus;
        }

        return new TransactionResultDTO(currentTransactionStatus, String.join(", ", info));
    }

    private void checkEqualsTransactionStatus (Set<String> info, TransactionStatus previous, TransactionStatus current) {
        if(previous != current) {
            info.clear();
        }
    }

    private TransactionStatus getTransactionStatusByAmount(Long amount) {
        return Arrays.stream(TransactionStatus.values())
                .filter(num -> Math.max(num.getMinAmount(), amount)
                        == Math.min(amount, num.getMaxAmount()))
                .findFirst()
                .orElseThrow(TransactionException::new);
    }

}
