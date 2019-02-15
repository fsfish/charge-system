package zwt.charge.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import zwt.charge.domain.ChargePrice;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午4:05 2019/1/6
 */
public interface ChargePriceRepository extends JpaRepository<ChargePrice, Long>,
        QuerydslPredicateExecutor<ChargePrice> {
}
