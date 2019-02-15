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
 * @Description: 机构可用额度预警
 * @Date: Created in 下午5:22 2019/1/25
 */
@Table(name = "charge_warning")
@Entity
@Setter
@Getter
public class ChargeWarning implements Serializable {

    private static final long serialVersionUID = -3836322107382433195L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    // 用户id
    private Long userId;

    // 用户姓名
    private String username;

    // 用户手机号
    private String mobile;

    private Long warningMoney;

    private boolean flag;

    private Date createTime;

}
