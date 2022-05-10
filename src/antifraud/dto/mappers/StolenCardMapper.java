package antifraud.dto.mappers;

import antifraud.dto.StolenCardDTO;
import antifraud.entities.StolenCard;
import org.springframework.stereotype.Component;

@Component
public class StolenCardMapper {

    public StolenCard stolenCardDTOToStolenCard(StolenCardDTO stolenCardDTO) {
        StolenCard stolenCard = new StolenCard();
        stolenCard.setId(stolenCardDTO.getId());
        stolenCard.setNumber(stolenCardDTO.getNumber());
        return stolenCard;
    }

    public StolenCardDTO stolenCardToStolenCardDTO(StolenCard stolenCard) {
        StolenCardDTO stolenCardDTO = new StolenCardDTO();
        stolenCardDTO.setId(stolenCard.getId());
        stolenCardDTO.setNumber(stolenCard.getNumber());
        return stolenCardDTO;
    }

}
