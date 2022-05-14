package antifraud.services.processors;

import antifraud.utils.TransactionStatusesWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LimitProcessor {

    @Getter
    private TransactionStatusesWrapper transactionStatusesWrapper;

    private LimitProcessor nextProcessor;

    @Autowired
    public LimitProcessor(TransactionStatusesWrapper transactionStatusesWrapper) {
        this.transactionStatusesWrapper = transactionStatusesWrapper;
    }

    public void changeLimit(long newLimit) {
        if (nextProcessor != null) {
            nextProcessor.changeLimit(newLimit);
        }
    }

    public void add(LimitProcessor processor) {
        if (nextProcessor != null) {
            nextProcessor.add(processor);
        } else {
            nextProcessor = processor;
        }
    }

    public void clean() {
        this.nextProcessor.nextProcessor = null;
        this.nextProcessor = null;
    }

}
