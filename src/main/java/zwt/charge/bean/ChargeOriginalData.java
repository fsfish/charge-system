package zwt.charge.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: lilongzhou
 * @Description: 从数据队列读取到的元数据
 * @Date: Created in 下午5:16 2019/1/10
 */
@Setter
@Getter
public class ChargeOriginalData {

    /**话单传递数据**/

    // 设备硬件别名（唯一性的）
    private String id;

    // 设备使用流名称
    private String streamId;

    // 设备使用流类型 音视频流 a代表音频 v代表视频
    private String streamType;

    // 话单开始时间
    private String startTime;

    // 话单结束时间
    private String endTime;

    // 使用流量大小
    private long flow;

    /**获取到数据后的扩展数据**/

    // 创建时间
    private Date createTime;

    // 原始数据的时间戳
    private long originTimestamp;

}
