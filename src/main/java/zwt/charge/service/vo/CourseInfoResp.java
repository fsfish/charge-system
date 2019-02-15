package zwt.charge.service.vo;

import lombok.Getter;
import lombok.Setter;
import zwt.charge.domain.Course;
import zwt.charge.domain.Device;


/**
 * @Author: lilongzhou
 * @Description: 当前课程中的老师和学生信息等
 * @Date: Created in 11:33 2018/7/25
 */
@Setter
@Getter
public class CourseInfoResp {

    private DeviceVO deviceVO;

    private Long courseId;

    private AgencyChargeVO agencyChargeVO;

}
