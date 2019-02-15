package zwt.charge.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import zwt.charge.domain.CourseConfig;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午4:09 2018/10/24
 */
public interface CourseConfigRepository extends
        JpaRepository<CourseConfig, Long>, QuerydslPredicateExecutor<CourseConfig> {

}
