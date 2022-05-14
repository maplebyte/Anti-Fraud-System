package antifraud.services.processors;

import antifraud.utils.TransactionStatus;
import antifraud.utils.TransactionStatusesWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllowedProcessor extends LimitProcessor{

    public AllowedProcessor(TransactionStatusesWrapper transactionStatusesWrapper) {
        super(transactionStatusesWrapper);
    }

    public void changeLimit(long newLimit) {
        super.getTransactionStatusesWrapper().changeMaxLimitByTransactionStatus(TransactionStatus.ALLOWED, newLimit);
        super.changeLimit(newLimit);
    }

    public void clean() {
        super.clean();
    }

}
