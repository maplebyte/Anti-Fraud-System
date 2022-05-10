package antifraud.respositories;

import antifraud.models.StolenCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StolenCardRepository extends JpaRepository<StolenCard, Long> {

    boolean existsByNumber(String ip);

    @Transactional
    void deleteByNumber(String number);

}
