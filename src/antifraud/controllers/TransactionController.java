package antifraud.controllers;

import antifraud.dto.TransactionDTO;
import antifraud.dto.TransactionResultDTO;
import antifraud.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/api/antifraud/transaction")
    public ResponseEntity<TransactionResultDTO> transaction(@RequestBody @Valid TransactionDTO transaction) {
        log.info("Incoming transaction {}", transaction);
        return new ResponseEntity<>(transactionService.validate(transaction), HttpStatus.OK);
    }

}
