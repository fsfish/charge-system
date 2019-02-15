package zwt.charge.service.vo;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import zwt.charge.domain.CourseDeviceConfig;

import java.util.Map;

@Getter
@Setter
public class DeviceVO {

    private Long id;

    private String alias;

    private Long agencyId;

    //用户设置的设备别名
    private String nickname;

    //设备地址
    private String address;

    private String uuid;

    private int type;

    // 设备状态 true 静音状态 false 非静音状态
    //private boolean mute;
    // 设备在课程中配置信息
    private CourseDeviceConfig courseDeviceConfig;

    private Map<String, String> config = Maps.newTreeMap();

    // apk版本号
    private Integer apkVersion;
}
