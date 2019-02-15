package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: lilongzhou
 * @Description: 用户课程关联表
 * @Date: Created in 下午12:39 2018/9/14
 */
@Table(name = "user_course")
@Entity
@Setter
@Getter
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long courseId;

}
