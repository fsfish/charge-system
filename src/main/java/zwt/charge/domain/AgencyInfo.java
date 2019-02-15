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
 * @Description: 机构账户信息
 * @Date: Created in 下午5:26 2019/1/25
 */
@Table(name = "charge_info")
@Entity
@Setter
@Getter
public class AgencyInfo implements Serializable {

    private static final long serialVersionUID = -8255969487244965L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    private long balanceMoney;

    private long presentMoney;

    private long creditMoney;

    private Date createTime;

}
