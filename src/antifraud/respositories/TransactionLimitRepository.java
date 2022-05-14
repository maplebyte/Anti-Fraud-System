package antifraud.respositories;

import antifraud.entities.TransactionLimit;
import antifraud.utils.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLimitRepository extends JpaRepository<TransactionLimit, TransactionStatus> {

    boolean existsByTransactionStatus(TransactionStatus transactionStatus);

}
