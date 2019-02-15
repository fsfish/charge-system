package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午3:39 2018/10/24
 */
@Entity
@Table(name = "course_device_config", indexes = {@Index(columnList = "courseId"), @Index(columnList = "deviceId")})
@Getter
@Setter
public class CourseDeviceConfig implements Serializable {

    private static final long serialVersionUID = 5320906965637334102L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 课程id
    @Column(unique = true)
    private Long courseId;

    // 设备id
    @Column(unique = true)
    private Long deviceId;

    // 教师拉取该设备的声音 1L 表示拉取 2L 表示不拉取
    private Long tpVoice;

    // 教师拉取该设备的视频 1L 表示拉取 2L 表示不拉取
    private Long tpVideo;

    // 学生拉取该设备的声音 1L 表示拉取 2L 表示不拉取
    private Long spVoice;

    // 学生拉取该设备的视频 1L 表示拉取 2L 表示不拉取
    private Long spVideo;

}
