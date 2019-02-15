package zwt.charge.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import zwt.charge.domain.CourseDevice;

import java.util.List;

public interface CourseDeviceRepository extends JpaRepository<CourseDevice, Long>, QuerydslPredicateExecutor<CourseDevice> {

    List<CourseDevice> findAllByCourseIdOrderByIdAsc(Long courseId);

    void deleteAllByCourseId(Long courseId);

    void deleteAllByCourseIdAndDeviceId(Long courseId, Long id);
}
