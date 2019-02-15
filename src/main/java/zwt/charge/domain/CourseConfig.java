package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午3:34 2018/10/24
 */
@Entity
@Table(name = "course_config")
@Getter
@Setter
public class CourseConfig implements Serializable {

    private static final long serialVersionUID = -2621742253193124986L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long courseId;

    //课程的状态 0 结束, 1 开始, 2 未开始
    private Long courseStatus = 0L;

    // 是否静音 true静音状态 false 非静音状态
    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean mute;

    // 发送ppt状态, 0 不接收 1 接收
    private Long pptStatus;

}
