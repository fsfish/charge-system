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
import java.util.Date;

@Entity
@Table(name = "course")
@Getter
@Setter
public class Course implements Serializable {

    private static final long serialVersionUID = -682204254004816175L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseNumber;

    //课程标题
    private String title;

    //老师设备的id
    private Long teacherId;

    //机构的id
    private Long agencyId;

    private Date createTime;

    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean deleted = false;

    private Date startTime;

    private Date endTime;

    //课程的状态 0 结束, 1 开始, 2 未开始
    //private Long courseStatus = 0L;

    // 是否静音 true静音状态 false 非静音状态
    //@Column(columnDefinition = "tinyint(4) default 0")
    //private boolean mute;

    // 发送ppt状态, 0 不接收 1 接收
    //private Long pptStatus;
}
