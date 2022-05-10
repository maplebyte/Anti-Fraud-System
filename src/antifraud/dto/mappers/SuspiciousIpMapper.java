package antifraud.dto.mappers;

import antifraud.dto.SuspiciousIpDTO;
import antifraud.entities.SuspiciousIp;
import org.springframework.stereotype.Component;

@Component
public class SuspiciousIpMapper {

    public SuspiciousIp suspiciousIpDTOToSuspiciousIp(SuspiciousIpDTO suspiciousIpDTO) {
        SuspiciousIp suspiciousIp = new SuspiciousIp();
        suspiciousIp.setId(suspiciousIpDTO.getId());
        suspiciousIp.setIp(suspiciousIpDTO.getIp());
        return suspiciousIp;
    }

    public SuspiciousIpDTO suspiciousIpToSuspiciousIpDTO(SuspiciousIp suspiciousIp) {
        SuspiciousIpDTO suspiciousIpDTO = new SuspiciousIpDTO();
        suspiciousIpDTO.setId(suspiciousIp.getId());
        suspiciousIpDTO.setIp(suspiciousIp.getIp());
        return suspiciousIpDTO;
    }

}
