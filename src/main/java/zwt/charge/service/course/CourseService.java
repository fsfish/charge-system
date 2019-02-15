package zwt.charge.service.course;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zwt.charge.dao.CourseConfigRepository;
import zwt.charge.dao.CourseRepository;
import zwt.charge.domain.CourseConfig;
import zwt.charge.domain.QCourseConfig;

import java.util.Optional;

/**
 * @Author: lilongzhou
 * @Description: 课程业务层
 * @Date: Created in 下午1:55 2019/1/23
 */
@Service
@Slf4j
public class CourseService {

    @Autowired
    private CourseConfigRepository courseConfigRepository;

    public CourseConfig getCourseConfig(Long courseId) {
        CourseConfig courseConfig = new CourseConfig();
        QCourseConfig qcourseConfig = QCourseConfig.courseConfig;
        Predicate predicate = qcourseConfig.courseId.eq(courseId);
        Optional<CourseConfig> optional = courseConfigRepository.findOne(predicate);
        if (optional.isPresent()) {
            courseConfig = optional.get();
        }
        return courseConfig;
    }

}
