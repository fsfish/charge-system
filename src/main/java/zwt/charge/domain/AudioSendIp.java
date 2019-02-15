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
 * @Description:
 * @Date: Created in 下午3:00 2018/11/13
 */
@Table(name = "audio_send_ip")
@Entity
@Getter
@Setter
public class AudioSendIp implements Serializable {

    private static final long serialVersionUID = -1890140358999704203L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    private Date createTime;

}
