package antifraud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class SameFeedbackAndStatusValueException extends RuntimeException {

    public SameFeedbackAndStatusValueException() {
        super("Exception: Feedback is the same as status");
    }


}
