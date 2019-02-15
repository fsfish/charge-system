package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 10:55 2018/7/29
 */
@Entity
@Table(name = "deviceToDeviceConfiguration", indexes = {@Index(columnList = "deviceId")})
@Setter
@Getter
public class DeviceToDeviceConfiguration implements Serializable {

    private static final long serialVersionUID = 7854770450478074199L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long deviceId;

    private Long deviceConfigId;

}
