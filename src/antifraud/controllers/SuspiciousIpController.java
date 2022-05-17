package antifraud.controllers;

import antifraud.dto.StatusResultDTO;
import antifraud.dto.SuspiciousIpDTO;
import antifraud.services.SuspiciousIpService;
import antifraud.validation.patterns.PatternsValidatorUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("api/antifraud")
@Validated
@Slf4j
@Tag(name = "Suspicious IP Controller", description = "Controller enables anti-fraud system to retrieve a list of suspicious IP address to ban them from carrying out any transactions.")
public class SuspiciousIpController {

    private final SuspiciousIpService suspiciousIpService;

    @Autowired
    public SuspiciousIpController(SuspiciousIpService securityService) {
        this.suspiciousIpService = securityService;
    }

    @Operation(summary = "Saves a suspicious IP address")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns a SuspiciousIpDTO"),
            @ApiResponse(responseCode = "201", description = "Successfully saved"),
            @ApiResponse(responseCode = "409", description = "The suspicious IP address is already in database"),
            @ApiResponse(responseCode = "400", description = "The suspicious IP address has the wrong format")
    })
    @PostMapping("/suspicious-ip")
    public ResponseEntity<SuspiciousIpDTO> saveIp(@RequestBody @Valid SuspiciousIpDTO suspiciousIpDTO) {
        log.info("Incoming ip {} ", suspiciousIpDTO);
        SuspiciousIpDTO savedSuspiciousIpDTO = suspiciousIpService.saveSuspiciousIp(suspiciousIpDTO);
        return new ResponseEntity<>(savedSuspiciousIpDTO, HttpStatus.OK);
    }

    @Operation(summary = "Deletes a suspicious IP address")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns a StatusResultDTO"),
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "The suspicious IP address not found"),
            @ApiResponse(responseCode = "400", description = "The suspicious IP address number has the wrong format")
    })
    @DeleteMapping("/suspicious-ip/{ip}")
    public ResponseEntity<StatusResultDTO> removeIp(@PathVariable @Pattern(regexp = PatternsValidatorUtil.IP_FORMAT,
                                                                message = "Invalid IP format.")
                                                                String ip) {
        suspiciousIpService.removeSuspiciousIp(ip);
        return new ResponseEntity<>(new StatusResultDTO.SuspiciousIpRemoved(ip), HttpStatus.OK);
    }

    @Operation(summary = "Gets all suspicious IP address")
    @ApiResponses(value = {
            @ApiResponse(description = "List<SuspiciousIpDTO>"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    })
    @GetMapping("/suspicious-ip")
    public ResponseEntity<List<SuspiciousIpDTO>> getAll() {
        return new ResponseEntity<>(suspiciousIpService.getAllSuspiciousIp(), HttpStatus.OK);
    }

}
