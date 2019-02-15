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
 * @Description: 计费月表
 * @Date: Created in 下午1:18 2019/1/4
 */
@Table(name = "charge_months")
@Entity
@Setter
@Getter
public class ChargeMonths implements Serializable {

    private static final long serialVersionUID = 1344195631360346128L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    // 当月上课数量
    private Integer monthsCourseNum;

    // 当月上课时长
    private long monthsCourseDuration;

    // 当月消费数据
    private long monthsConsumer;

    // 当月余额
    private long monthsRemain;

    // 当月赠送数据
    private long presentMoney;

    // 当月信用数据
    private long creditMoney;

    // 当月充值数据
    private long monthsRecharge;

    private Date modifyTime;

    private Date createTime;

}
