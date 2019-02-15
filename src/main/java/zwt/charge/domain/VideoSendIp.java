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
 * @Date: Created in 下午3:01 2018/11/13
 */
@Table(name = "video_send_ip")
@Entity
@Getter
@Setter
public class VideoSendIp implements Serializable {

    private static final long serialVersionUID = -6323600972826074676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    private Date createTime;

}
