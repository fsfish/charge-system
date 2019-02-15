package zwt.charge.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午5:09 2018/12/26
 */
@Setter
@Getter
public class ProduceData {

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


}
