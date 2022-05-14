package antifraud.services.resolovers;

import antifraud.services.processors.AllowedProcessor;
import antifraud.services.processors.LimitProcessor;
import antifraud.services.processors.ManualProcessor;
import antifraud.services.processors.ProhibitedProcessor;
import antifraud.utils.TransactionStatus;
import antifraud.utils.TransactionStatusesWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeedbackResolver {

    private final LimitProcessor limitProcessor;
    private final AllowedProcessor allowedProcessor;
    private final ManualProcessor manualProcessor;
    private final ProhibitedProcessor prohibitedProcessor;
    private final TransactionStatusesWrapper transactionStatusesWrapper;

    @Autowired
    public FeedbackResolver(LimitProcessor limitProcessor, AllowedProcessor allowedProcessor,
                            ManualProcessor manualProcessor, ProhibitedProcessor prohibitedProcessor,
                            TransactionStatusesWrapper transactionStatusesWrapper) {
        this.limitProcessor = limitProcessor;
        this.allowedProcessor = allowedProcessor;
        this.manualProcessor = manualProcessor;
        this.prohibitedProcessor = prohibitedProcessor;
        this.transactionStatusesWrapper = transactionStatusesWrapper;
    }

    private static long increaseLimit(long currentLimit, long valueFromTransaction) {
        return (long) Math.ceil(0.8 * currentLimit + 0.2 * valueFromTransaction);
    }

    private static long decreaseLimit(long currentLimit, long valueFromTransaction) {
        return (long) Math.ceil(0.8 * currentLimit - 0.2 * valueFromTransaction);
    }

    public void updateLimit(TransactionStatus feedback, TransactionStatus status, long amount) {
        long result = 0;
        if (feedback.equals(TransactionStatus.ALLOWED)) {
            if (status.equals(TransactionStatus.MANUAL_PROCESSING)) {
                result = increaseLimit(transactionStatusesWrapper.getMaxAmountByTransactionStatus(TransactionStatus.ALLOWED), amount);
                limitProcessor.add(allowedProcessor);
            } else if (status.equals(TransactionStatus.PROHIBITED)) {
                result = increaseLimit(transactionStatusesWrapper.getMaxAmountByTransactionStatus(TransactionStatus.ALLOWED), amount);
                limitProcessor.add(allowedProcessor);
                limitProcessor.changeLimit(result);
                limitProcessor.clean();
                result = increaseLimit(transactionStatusesWrapper.getMaxAmountByTransactionStatus(TransactionStatus.MANUAL_PROCESSING), amount);
                manualProcessor.setDecrease(false);
                limitProcessor.add(manualProcessor);
            }
        } else if (feedback.equals(TransactionStatus.MANUAL_PROCESSING)) {
            if (status.equals(TransactionStatus.ALLOWED)) {
                result = decreaseLimit(transactionStatusesWrapper.getMaxAmountByTransactionStatus(TransactionStatus.ALLOWED), amount);
                limitProcessor.add(allowedProcessor);
                manualProcessor.setDecrease(true);
                limitProcessor.add(manualProcessor);
            } else if (status.equals(TransactionStatus.PROHIBITED)) {
                result = increaseLimit(transactionStatusesWrapper.getMaxAmountByTransactionStatus(TransactionStatus.MANUAL_PROCESSING), amount);
                manualProcessor.setDecrease(false);
                limitProcessor.add(manualProcessor);
                limitProcessor.add(prohibitedProcessor);
            }
        } else if (feedback.equals(TransactionStatus.PROHIBITED)) {
            if (status.equals(TransactionStatus.ALLOWED)) {
                result = decreaseLimit(transactionStatusesWrapper.getMaxAmountByTransactionStatus(TransactionStatus.ALLOWED), amount);
                limitProcessor.add(allowedProcessor);
                manualProcessor.setDecrease(true);
                limitProcessor.add(manualProcessor);
                limitProcessor.changeLimit(result);
                limitProcessor.clean();
                result = decreaseLimit(transactionStatusesWrapper.getMaxAmountByTransactionStatus(TransactionStatus.MANUAL_PROCESSING), amount);
                manualProcessor.setDecrease(false);
                limitProcessor.add(manualProcessor);
                limitProcessor.add(prohibitedProcessor);
            } else if (status.equals(TransactionStatus.MANUAL_PROCESSING)) {
                result = decreaseLimit(transactionStatusesWrapper.getMaxAmountByTransactionStatus(TransactionStatus.MANUAL_PROCESSING), amount);
                manualProcessor.setDecrease(false);
                limitProcessor.add(manualProcessor);
                limitProcessor.add(prohibitedProcessor);
            }
        }
        limitProcessor.changeLimit(result);
        limitProcessor.clean();
    }


}
