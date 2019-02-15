package zwt.charge.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 上午11:37 2019/1/18
 */
@Setter
@Getter
public class DurationBean {

    /**获取到数据后的扩展数据**/

    private Long id;

    private Long agencyId;

    private Long courseId;

    // 设备编号
    private String deviceAlias;

    // 订阅设备流名称 -> 订阅的设备
    private String deviceStreamName;

    // 订阅设备流类型 -> 流的类型，a为音频、v为视频、av为音视频混合类型
    private String deviceStreamType;

    // 设备类型
    private Integer deviceType;

    // 设备流开始使用时间
    private Date streamStartUsedTime;

    // 设备流结束使用时间
    private Date streamEndUsedTime;

    // 设备流使用时长
    private long durations;

    // 设备流使用流量大小
    private long flowUsed;

    // 时长消费金额
    private long deviceStreamConsumerMoney;

    // 流量消费金额
    private long flowConsumerMoney;

    // 开始时间
    private Date createTime;

    // 修改时间
    private Date modifyTime;

    // 消费的充值金额
    private long rechargeMoney;

    // 消费的赠送金额
    private long presentMoney;

    // 消费的信用额度
    private long creditMoney;

}
