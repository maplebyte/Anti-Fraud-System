package antifraud.validation.patterns;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatternsValidatorUtil {
    //match IPv4 address format
    public static final String IP_FORMAT = "((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}";
}
