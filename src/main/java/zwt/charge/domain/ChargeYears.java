package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lilongzhou
 * @Description: 计费年表
 * @Date: Created in 下午1:18 2019/1/4
 */
@Table(name = "charge_years")
@Entity
@Setter
@Getter
public class ChargeYears implements Serializable {

    private static final long serialVersionUID = 1799047559635969801L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    // 当年上课数量
    private Integer yearsCourseNum;

    // 当年上课时长
    private long yearsCourseDuration;

    // 当年消费数据
    private long yearsConsumer;

    // 当年余额
    private long yearsRemain;

    // 当年赠送数据
    private long presentMoney;

    // 当年信用数据
    private long creditMoney;

    // 当年充值数据
    private long yearsRecharge;

    private Date modifyTime;

    private Date createTime;



}
