package zwt.charge.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import zwt.charge.domain.Device;

public interface DeviceRepository extends JpaRepository<Device, Long>, QuerydslPredicateExecutor<Device> {

    Device findByAlias(String alias);

    Page<Device> findAllByDeletedAndAgencyIdOrderByIdDesc(Pageable pageable, boolean deleted, Long agencyId);

}
