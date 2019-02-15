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

@Entity
@Table(name = "course_device", indexes = {@Index(columnList = "courseId"), @Index(columnList = "agencyId")})
@Setter
@Getter
public class CourseDevice implements Serializable {

    private static final long serialVersionUID = 4023660329805773142L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //学生设备的id
    private Long deviceId;

    //课程id
    @Column(unique = true)
    private Long courseId;

    @Column(unique = true)
    private Long agencyId;
}
