package antifraud.utils;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    ALLOWED(1, 200),
    MANUAL_PROCESSING(201, 1500),
    PROHIBITED(1501, Long.MAX_VALUE);

    private long minAmount;
    private long maxAmount;

    TransactionStatus(long minAmount, long maxAmount) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

}
