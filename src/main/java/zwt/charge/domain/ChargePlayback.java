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
 * @Description: 回放服务表
 * @Date: Created in 下午3:28 2019/1/4
 */
@Table(name = "charge_playback")
@Entity
@Setter
@Getter
public class ChargePlayback implements Serializable {

    private static final long serialVersionUID = 300003355370654466L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    private Long courseId;

    // 存储大小
    private long storeSize;

    // 使用流量大小
    private long flowSize;

    private Date createTime;

    private Date modifyTime;

}
