package zwt.charge.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import zwt.charge.domain.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Long>, QuerydslPredicateExecutor<Agency> {

    Agency findTopByDeleted(boolean deleted);

    Agency findByName(String name);
}
