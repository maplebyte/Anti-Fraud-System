package antifraud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatusResultDTO {

    @NotEmpty(message = "Status mustn't be empty")
    @NotNull(message = "Status mustn't be null")
    private String status;

    public static class SuspiciousIpRemoved extends StatusResultDTO {
        public SuspiciousIpRemoved(String ip) {
            super(String.format("IP %s successfully removed!", ip));
        }
    }

    public static class StolenCardRemoved extends StatusResultDTO {
        public StolenCardRemoved(String number) {
            super(String.format("Card %s successfully removed!", number));
        }
    }

}
