package antifraud.services.processors;

import antifraud.utils.TransactionStatus;
import antifraud.utils.TransactionStatusesWrapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ManualProcessor extends LimitProcessor {

    @Setter
    @Getter
    private boolean isDecrease;

    public ManualProcessor(TransactionStatusesWrapper transactionStatusesWrapper) {
        super(transactionStatusesWrapper);
    }

    @Override
    public void changeLimit(long newLimit) {
        if(isDecrease) {
            super.getTransactionStatusesWrapper().changeMinLimitByTransactionStatus(TransactionStatus.MANUAL_PROCESSING, newLimit + 1);
        } else {
            super.getTransactionStatusesWrapper().changeMaxLimitByTransactionStatus(TransactionStatus.MANUAL_PROCESSING, newLimit);
        }
        super.changeLimit(newLimit);
    }

    public void clean() {
        super.clean();
    }


}
