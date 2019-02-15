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
 * @Description: 计费消费表(以课程为标准来显示消费记录)
 * @Date: Created in 下午1:15 2019/1/4
 */
@Table(name = "charge_consumer")
@Entity
@Setter
@Getter
public class ChargeConsumer implements Serializable {

    private static final long serialVersionUID = -5607219740455643983L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    private Long courseId;

    // 保持幂等性的字段
    private String ticket;

    // 课程使用时长
    private long duration;

    // 课程使用流量
    private long flow;

    // 课程时长消费
    private long durationCharge;

    // 课程使用流量时长消费
    private long flowCharge;

    // 总消费
    private long consumerMoney;

    private Date modifyTime;

    private Date createTime;

    // 产生消费的时间，原始数据中的时间
    private Date productTime;

}
