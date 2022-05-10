package antifraud.controllers;

import antifraud.dto.StatusResultDTO;
import antifraud.dto.SuspiciousIpDTO;
import antifraud.services.SuspiciousIpService;
import antifraud.validation.patterns.PatternsValidatorUtil;
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
@Validated
@Slf4j
public class SuspiciousIpController {

    private final SuspiciousIpService suspiciousIpService;

    @Autowired
    public SuspiciousIpController(SuspiciousIpService securityService) {
        this.suspiciousIpService = securityService;
    }

    @PostMapping("api/antifraud/suspicious-ip")
    public ResponseEntity<SuspiciousIpDTO> saveIp(@RequestBody @Valid SuspiciousIpDTO suspiciousIpDTO) {
        log.info("Incoming ip {} ", suspiciousIpDTO);
        SuspiciousIpDTO savedSuspiciousIpDTO = suspiciousIpService.saveSuspiciousIp(suspiciousIpDTO);
        return new ResponseEntity<>(savedSuspiciousIpDTO, HttpStatus.OK);
    }

    @DeleteMapping("/api/antifraud/suspicious-ip/{ip}")
    public ResponseEntity<StatusResultDTO> removeIp(@PathVariable @Pattern(regexp = PatternsValidatorUtil.IP_FORMAT,
                                                                message = "Invalid IP format.")
                                                                String ip) {
        suspiciousIpService.removeSuspiciousIp(ip);
        return new ResponseEntity<>(new StatusResultDTO.SuspiciousIpRemoved(ip), HttpStatus.OK);
    }

    @GetMapping("/api/antifraud/suspicious-ip")
    public ResponseEntity<List<SuspiciousIpDTO>> getAll() {
        return new ResponseEntity<>(suspiciousIpService.getAllSuspiciousIp(), HttpStatus.OK);
    }

}
