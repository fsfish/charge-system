package zwt.charge.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import zwt.charge.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long>, QuerydslPredicateExecutor<Course> {

    Page<Course> findAllByAgencyId(Pageable pageable, Long agencyId);
}
