package antifraud.services.processors;

import antifraud.utils.TransactionStatus;
import antifraud.utils.TransactionStatusesWrapper;
import org.springframework.stereotype.Component;

@Component
public class ProhibitedProcessor extends LimitProcessor {

    public ProhibitedProcessor(TransactionStatusesWrapper transactionStatusesWrapper) {
        super(transactionStatusesWrapper);
    }

    @Override
    public void changeLimit(long newLimit) {
        super.getTransactionStatusesWrapper().changeMinLimitByTransactionStatus(TransactionStatus.PROHIBITED, newLimit + 1);
        super.changeLimit(newLimit);
    }

    @Override
    public void clean() {
        super.clean();
    }

}
