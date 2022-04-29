package antifraud.controllers;

import antifraud.dto.ResultDTO;
import antifraud.dto.TransactionDTO;
import antifraud.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/api/antifraud/transaction")
    public ResponseEntity<ResultDTO> transaction(@RequestBody TransactionDTO transaction) {
        return new ResponseEntity<>(transactionService.validate(transaction), HttpStatus.OK);
    }

}
