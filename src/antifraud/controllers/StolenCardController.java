package antifraud.controllers;

import antifraud.dto.StatusResultDTO;
import antifraud.dto.StolenCardDTO;
import antifraud.services.StolenCardService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Slf4j
public class StolenCardController {

    private final StolenCardService stolenCardService;

    @Autowired
    public StolenCardController(StolenCardService stolenCardService) {
        this.stolenCardService = stolenCardService;
    }

    @PostMapping("/api/antifraud/stolencard")
    public ResponseEntity<StolenCardDTO> saveStolenCard(@RequestBody @Valid StolenCardDTO stolenCardDTO) {
        log.info("Incoming card {} ", stolenCardDTO);
        StolenCardDTO savedStolenCard = stolenCardService.saveStolenCard(stolenCardDTO);
        return new ResponseEntity<>(savedStolenCard, HttpStatus.OK);
    }

    @DeleteMapping("/api/antifraud/stolencard/{number}")
    public ResponseEntity<StatusResultDTO> removeIp(@PathVariable @LuhnCheck(message = "Invalid card number.") String number) {
        stolenCardService.removeStolenCard(number);
        return new ResponseEntity<>(new StatusResultDTO.StolenCardRemoved(number), HttpStatus.OK);
    }

    @GetMapping("/api/antifraud/stolencard")
    public ResponseEntity<List<StolenCardDTO>> getAll() {
        return new ResponseEntity<>(stolenCardService.getAllStolenCard(), HttpStatus.OK);
    }

}
