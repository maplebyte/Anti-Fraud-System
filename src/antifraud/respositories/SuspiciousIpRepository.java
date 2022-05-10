package antifraud.respositories;

import antifraud.entities.SuspiciousIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SuspiciousIpRepository extends JpaRepository<SuspiciousIp, Long> {

    boolean existsByIp(String ip);

    @Transactional
    void deleteByIp(String ip);

}
