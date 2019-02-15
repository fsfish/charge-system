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
 * @Date: Created in 下午3:17 2018/11/13
 */
@Table(name = "device_config_to_master_ip")
@Entity
@Getter
@Setter
public class DeviceConfigToMasterIp implements Serializable {

    private static final long serialVersionUID = -739281960104688326L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long deviceConfigId;

    private Long masterIpId;

}
