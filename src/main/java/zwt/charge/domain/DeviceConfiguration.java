package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lilongzhou
 * @Description: 设备配置信息
 * @Date: Created in 16:33 2018/7/25
 */
@Table(name = "device_config")
@Entity
@Setter
@Getter
public class DeviceConfiguration implements Serializable {

    private static final long serialVersionUID = -8734073398735997620L;

    /**
     * 设备配置自增长id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 机构id
     */
    private Long agencyId;

    private String ip;

    private Integer port;

    /**
     * 音频fec缓存，如果有马赛克，增加该数据 一般设置20，该参数也影响到服务器的cache
     */
    private Integer audioFecCach;

    /**
     * 视频fec缓存，如果有马赛克，增加该数据 一般设置20，该参数也影响到服务器的cache
     */
    private Integer videoFecCach;

    /**
     * 在带宽足够的情况下音频卡顿设置该参数，一般不设置，
     */
    private Integer audioFecRate;

    /**
     * 在带宽足够的情况下有马赛克设置该参数，一般不设置，
     */
    private Integer videoFecRate;

    private Integer maxSendNums;

    private Integer maxSendTime;

    private Integer maxSendBandWidth;

    private Integer previewWidth;

    private Integer previewHeigth;

    /**
     * 帧率一般配置30
     */
    private Integer framerate;

    /**
     * 码流
     */
    private Integer bitrate;

    private String deviceName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 设备配置删除
     */
    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean deleted = false;

    /**
     * 设备配置名称
     */
    private String deviceConfigName;

    /**
     * 设备配置类型
     */
    @Column(columnDefinition = "tinyint(4) default 0")
    private int type = 0;

    @Getter
    public enum Type {

        STUDENT(0),

        TEACHER(1);

        private int id;

        Type(int id) {
            this.id = id;
        }
    }
}
