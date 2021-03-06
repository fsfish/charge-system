package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午3:18 2018/11/13
 */
@Table(name = "device_config_to_audio_send_ip")
@Entity
@Getter
@Setter
public class DeviceConfigToAudioSendIp implements Serializable {

    private static final long serialVersionUID = -1982629504323258602L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long deviceConfigId;

    private Long audioSendIpId;

}
