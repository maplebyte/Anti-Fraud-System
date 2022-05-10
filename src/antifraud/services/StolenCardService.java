package antifraud.services;

import antifraud.dto.StolenCardDTO;
import antifraud.dto.mappers.StolenCardMapper;
import antifraud.exceptions.EntityAlreadyExistException;
import antifraud.exceptions.EntityNotFoundException;
import antifraud.exceptions.EntityNullPointerException;
import antifraud.entities.StolenCard;
import antifraud.respositories.StolenCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StolenCardService {

    private final StolenCardRepository stolenCardRepository;
    private final StolenCardMapper stolenCardMapper;

    @Autowired
    public StolenCardService(StolenCardRepository stolenCardRepository, StolenCardMapper stolenCardMapper) {
        this.stolenCardRepository = stolenCardRepository;
        this.stolenCardMapper = stolenCardMapper;
    }

    public StolenCardDTO saveStolenCard(StolenCardDTO stolenCardDTO) {
        if (Objects.isNull(stolenCardDTO)) {
            throw new EntityNullPointerException();
        }
        if (stolenCardRepository.existsByNumber(stolenCardDTO.getNumber())) {
            throw new EntityAlreadyExistException(stolenCardDTO.getNumber());
        }
        StolenCard savedStolenCard = stolenCardMapper.stolenCardDTOToStolenCard(stolenCardDTO);
        stolenCardRepository.save(savedStolenCard);
        log.info("Saved {}", savedStolenCard);
        return stolenCardMapper.stolenCardToStolenCardDTO(savedStolenCard);
    }

    public void removeStolenCard(String number) {
        if (!stolenCardRepository.existsByNumber(number)) {
            throw new EntityNotFoundException(number);
        }
        stolenCardRepository.deleteByNumber(number);
        log.info("IP {} was deleted", number);
    }

    public List<StolenCardDTO> getAllStolenCard() {
        List<StolenCard> users = stolenCardRepository.findAll();
        return users
                .stream()
                .map(stolenCardMapper::stolenCardToStolenCardDTO)
                .collect(Collectors.toList());
    }

    public boolean isInBlacklist(String number) {
        return stolenCardRepository.existsByNumber(number);
    }

}
