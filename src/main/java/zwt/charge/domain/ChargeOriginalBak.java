package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lilongzhou
 * @Description: 计费原始数据备份
 * @Date: Created in 下午4:21 2018/12/24
 */
@Setter
@Getter
@Document(collection = "ChargeOriginalBak")
public class ChargeOriginalBak implements Serializable {

    // 设备硬件别名（唯一性的)
    private String deviceId;

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
