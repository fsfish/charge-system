package zwt.charge.service.device;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zwt.charge.core.utils.BeanCopyUtils;
import zwt.charge.dao.CourseDeviceRepository;
import zwt.charge.dao.CourseRepository;
import zwt.charge.dao.DeviceRepository;
import zwt.charge.domain.Course;
import zwt.charge.domain.CourseConfig;
import zwt.charge.domain.CourseDevice;
import zwt.charge.domain.Device;
import zwt.charge.domain.QCourse;
import zwt.charge.domain.QCourseDevice;
import zwt.charge.domain.QDevice;
import zwt.charge.service.agency.AgencyService;
import zwt.charge.service.course.CourseService;
import zwt.charge.service.vo.AgencyChargeVO;
import zwt.charge.service.vo.AgencyVO;
import zwt.charge.service.vo.BillingVO;
import zwt.charge.service.vo.DeviceVO;

import java.util.Optional;

/**
 * @Author: lilongzhou
 * @Description: 设备业务层
 * @Date: Created in 上午11:44 2019/1/23
 */
@Service
@Slf4j
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseDeviceRepository courseDeviceRepository;

    @Autowired
    private AgencyService agencyService;


    public DeviceVO getByAlias(String deviceAlias) {
        DeviceVO deviceVO = new DeviceVO();
        QDevice qdevice = QDevice.device;
        Predicate predicate = qdevice.deleted.eq(false)
                .and(qdevice.alias.eq(deviceAlias));
        Optional<Device> optional = deviceRepository.findOne(predicate);
        if (optional.isPresent()) {
            Device device = optional.get();
            deviceVO = BeanCopyUtils.copy(device, DeviceVO.class);
        } else {
            return deviceVO;
        }
        return deviceVO;
    }

    // 返回机构所需要的信息
    public AgencyChargeVO getInfoByAlias(String alias) {
        AgencyChargeVO agencyChargeVO = new AgencyChargeVO();
        DeviceVO deviceVO = getByAlias(alias);
        if (StringUtils.isNotEmpty(deviceVO.getAlias())) {
            // 查询机构信息
            AgencyVO agencyVO = agencyService.getAgencyById(deviceVO.getAgencyId());
            BillingVO billingVO = agencyService.getAgencyBilling(deviceVO.getAgencyId());
            if (deviceVO.getType() == 1) {
                Long courseId = getInfoByTeacherAlias(deviceVO.getId());
                agencyChargeVO.setAgencyId(deviceVO.getAgencyId());
                agencyChargeVO.setCourseId(courseId);
                if (agencyVO != null && agencyVO.getId() != null) {
                    agencyChargeVO.setAgencyId(deviceVO.getAgencyId());
                    agencyChargeVO.setAgencyName(agencyVO.getName());
                }
                if (billingVO != null) {
                    //agencyChargeVO.setAudioPrice();
                    //agencyChargeVO.setVideoPrice();
                    agencyChargeVO.setPrice(billingVO.getPrice());
                    //agencyChargeVO.setFlowPrice();
                }
            }
            // 学生设备名称
            if (deviceVO.getType() == 0) {
                Long courseId = getInfoByStudentAlias(deviceVO.getId());
                agencyChargeVO.setAgencyId(deviceVO.getAgencyId());
                agencyChargeVO.setCourseId(courseId);
                if (agencyVO != null && agencyVO.getId() != null) {
                    agencyChargeVO.setAgencyId(deviceVO.getAgencyId());
                    agencyChargeVO.setAgencyName(agencyVO.getName());
                }
                if (billingVO != null) {
                    //agencyChargeVO.setAudioPrice();
                    //agencyChargeVO.setVideoPrice();
                    agencyChargeVO.setPrice(billingVO.getPrice());
                    //agencyChargeVO.setFlowPrice();
                }

            }
            agencyChargeVO.setDeviceType(deviceVO.getType());
        }
        return agencyChargeVO;
    }


    public Long getInfoByTeacherAlias(Long deviceId) {
        Long courseId = null;
        QCourse qcourse = QCourse.course;
        Predicate predicate = qcourse.deleted.eq(false)
                .and(qcourse.teacherId.eq(deviceId));

        Iterable<Course> iterable = courseRepository.findAll(predicate);
        for (Course course : iterable) {
            CourseConfig courseConfig = courseService.getCourseConfig(course.getId());
            if (courseConfig.getCourseStatus() == 1) {
                courseId = courseConfig.getCourseId();
            }
        }
        return courseId;
    }

    public Long getInfoByStudentAlias(Long deviceId) {
        Long courseId = null;

        QCourseDevice qcourseDevice = QCourseDevice.courseDevice;
        Predicate predicate = qcourseDevice.deviceId.eq(deviceId);

        Iterable<CourseDevice> courseDeviceIterable = courseDeviceRepository.findAll(predicate);
        if (courseDeviceIterable.iterator().hasNext()) {
            for (CourseDevice courseDevice : courseDeviceIterable) {
                QCourse qcourse = QCourse.course;
                Predicate predicates = qcourse.deleted.eq(false)
                        .and(qcourse.id.eq(courseDevice.getCourseId()));
                Optional<Course> courseOptional = courseRepository.findOne(predicates);
                if (courseOptional.isPresent()) {
                    Course courses = courseOptional.get();
                    CourseConfig courseConfig = courseService.getCourseConfig(courses.getId());
                    if (courseConfig.getCourseStatus() == 1) {
                        courseId = courseConfig.getCourseId();
                    }
                }
            }
        }
        return courseId;
    }


}
