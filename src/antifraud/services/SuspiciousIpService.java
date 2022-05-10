package antifraud.services;

import antifraud.dto.SuspiciousIpDTO;
import antifraud.dto.mappers.SuspiciousIpMapper;
import antifraud.exceptions.EntityAlreadyExistException;
import antifraud.exceptions.EntityNotFoundException;
import antifraud.exceptions.EntityNullPointerException;
import antifraud.entities.SuspiciousIp;
import antifraud.respositories.SuspiciousIpRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SuspiciousIpService {

    private final SuspiciousIpRepository suspiciousIpRepository;
    private final SuspiciousIpMapper suspiciousIpMapper;

    @Autowired
    public SuspiciousIpService(SuspiciousIpRepository suspiciousIpRepository, SuspiciousIpMapper suspiciousIpMapper) {
        this.suspiciousIpRepository = suspiciousIpRepository;
        this.suspiciousIpMapper = suspiciousIpMapper;
    }

    public SuspiciousIpDTO saveSuspiciousIp(SuspiciousIpDTO suspiciousIpDTO) {
        if (Objects.isNull(suspiciousIpDTO)) {
            throw new EntityNullPointerException();
        }
        if (suspiciousIpRepository.existsByIp(suspiciousIpDTO.getIp())) {
            throw new EntityAlreadyExistException(suspiciousIpDTO.getIp());
        }
       SuspiciousIp savedSuspiciousIp = suspiciousIpMapper.suspiciousIpDTOToSuspiciousIp(suspiciousIpDTO);
       suspiciousIpRepository.save(savedSuspiciousIp);
       log.info("Saved {}", savedSuspiciousIp);
       return suspiciousIpMapper.suspiciousIpToSuspiciousIpDTO(savedSuspiciousIp);
    }

    public void removeSuspiciousIp(String ip) {
        if (!suspiciousIpRepository.existsByIp(ip)) {
            throw new EntityNotFoundException(ip);
        }
        suspiciousIpRepository.deleteByIp(ip);
        log.info("IP {} was deleted", ip);
    }

    public List<SuspiciousIpDTO> getAllSuspiciousIp() {
        List<SuspiciousIp> users = suspiciousIpRepository.findAll();
        return users
                .stream()
                .map(suspiciousIpMapper::suspiciousIpToSuspiciousIpDTO)
                .collect(Collectors.toList());
    }

    public boolean isInBlacklist(String ip) {
        return suspiciousIpRepository.existsByIp(ip);
    }

}
