package antifraud.controllers;

import antifraud.dto.StatusResultDTO;
import antifraud.dto.StolenCardDTO;
import antifraud.services.StolenCardService;
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
@Tag(name = "Stolen Card Controller", description = "Controller enables anti-fraud system to retrieve a list of prohibited card numbers to ban them from carrying out any transactions.")
public class StolenCardController {

    private final StolenCardService stolenCardService;

    @Autowired
    public StolenCardController(StolenCardService stolenCardService) {
        this.stolenCardService = stolenCardService;
    }

    @Operation(summary = "Saves a stolen card")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns a StolenCardDTO"),
            @ApiResponse(responseCode = "201", description = "Successfully saved"),
            @ApiResponse(responseCode = "409", description = "The card is already in database"),
            @ApiResponse(responseCode = "400", description = "The card number has the wrong format")
    })
    @PostMapping("/stolencard")
    public ResponseEntity<StolenCardDTO> saveStolenCard(@RequestBody @Valid StolenCardDTO stolenCardDTO) {
        log.info("Incoming card {} ", stolenCardDTO);
        StolenCardDTO savedStolenCard = stolenCardService.saveStolenCard(stolenCardDTO);
        return new ResponseEntity<>(savedStolenCard, HttpStatus.OK);
    }

    @Operation(summary = "Deletes a stolen card")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns a StatusResultDTO"),
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "The card not found"),
            @ApiResponse(responseCode = "400", description = "The card number has the wrong format")
    })
    @DeleteMapping("/stolencard/{number}")
    public ResponseEntity<StatusResultDTO> removeStolenCard(@PathVariable @LuhnCheck(message = "Invalid card number.") String number) {
        stolenCardService.removeStolenCard(number);
        log.info("Removed card with number {} ", number);
        return new ResponseEntity<>(new StatusResultDTO.StolenCardRemoved(number), HttpStatus.OK);
    }

    @Operation(summary = "Gets all stolen cards")
    @ApiResponses(value = {
            @ApiResponse(description = "List<StolenCardDTO>"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    })
    @GetMapping("/stolencard")
    public ResponseEntity<List<StolenCardDTO>> getAll() {
        return new ResponseEntity<>(stolenCardService.getAllStolenCard(), HttpStatus.OK);
    }

}
