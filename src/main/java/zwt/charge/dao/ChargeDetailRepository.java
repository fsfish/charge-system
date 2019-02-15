package zwt.charge.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import zwt.charge.domain.ChargeDetail;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午4:04 2019/1/6
 */
public interface ChargeDetailRepository extends JpaRepository<ChargeDetail, Long>,
        QuerydslPredicateExecutor<ChargeDetail> {
}
