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
 * @Description: 计费日表
 * @Date: Created in 下午1:17 2019/1/4
 */
@Table(name = "charge_days")
@Entity
@Setter
@Getter
public class ChargeDays implements Serializable {

    private static final long serialVersionUID = 3789251813584594607L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    // 当天上课数量
    private Integer daysCourseNum;

    // 当天上课时长
    private long daysCourseDuration;

    // 当天消费数据
    private long daysConsumer;

    // 当天余额
    private long daysRemain;

    // 当天赠送数据
    private long presentMoney;

    // 当天信用数据
    private long creditMoney;

    // 当天充值数据
    private long daysRecharge;

    private Date modifyTime;

    private Date createTime;


}
