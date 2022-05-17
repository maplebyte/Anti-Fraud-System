package antifraud.controllers;

import antifraud.dto.FeedBackDTO;
import antifraud.dto.TransactionDTO;
import antifraud.dto.TransactionResultDTO;
import antifraud.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/antifraud")
@Validated
@Slf4j
@Tag(name = "Transaction Controller", description = "Controller enables anti-fraud system to save transaction and add additional information such as feedback.")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Saves a new transaction with certain status")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns a TransactionResultDTO"),
            @ApiResponse(responseCode = "201", description = "Successfully saved"),
            @ApiResponse(responseCode = "400", description = "The card number has the wrong format")
    })
    @PostMapping("/transaction")
    public ResponseEntity<TransactionResultDTO> saveTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        log.info("Incoming transaction {}", transactionDTO);
        return new ResponseEntity<>(transactionService.validate(transactionDTO), HttpStatus.OK);
    }

    @Operation(summary = "Changes the limits in transaction statuses")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns a TransactionResultDTO"),
            @ApiResponse(responseCode = "201", description = "Successfully saved"),
            @ApiResponse(responseCode = "409", description = "The transaction is already in database"),
            @ApiResponse(responseCode = "422", description = "The feedback equals transaction status"),
            @ApiResponse(responseCode = "400", description = "The feedback has the wrong format (other than ALLOWED, MANUAL_PROCESSING, PROHIBITE)")
    })
    @PutMapping("/transaction")
    public ResponseEntity<TransactionDTO> addFeedback(@RequestBody @Valid FeedBackDTO feedBackDTO) {
        log.info("Incoming feedback {}", feedBackDTO);
        TransactionDTO transaction = transactionService.changeFraudLimits(feedBackDTO);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @Operation(summary = "Gets all transactions")
    @ApiResponses(value = {
            @ApiResponse(description = "List<TransactionDTO>"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    })
    @GetMapping("/history")
    public ResponseEntity<List<TransactionDTO>> getAll() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        log.info("All transactions" + transactions.toString());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Operation(summary = "Gets all transactions")
    @ApiResponses(value = {
            @ApiResponse(description = "List<TransactionDTO>"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "The transaction not found")
    })
    @GetMapping("/history/{number}")
    public ResponseEntity<List<TransactionDTO>> getTransactionByCardNumber(@PathVariable("number") @LuhnCheck(message = "Invalid card number.") String cardNumber) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByCardNumber(cardNumber);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
