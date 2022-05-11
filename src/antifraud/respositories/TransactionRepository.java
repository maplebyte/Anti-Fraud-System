package antifraud.respositories;

import antifraud.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByNumberAndIpNotAndDateBetween(
            String number,
            String ip,
            LocalDateTime publicationDateStart,
            LocalDateTime publicationDateEnd
    );

    List<Transaction> findAllByNumberAndWorldRegionNotAndDateBetween(
            String number,
            String ip,
            LocalDateTime publicationDateStart,
            LocalDateTime publicationDateEnd
    );

}
