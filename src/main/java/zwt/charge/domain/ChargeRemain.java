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
 * @Description: 计费余额表
 * @Date: Created in 下午1:16 2019/1/4
 */
@Table(name = "charge_remain")
@Entity
@Setter
@Getter
public class ChargeRemain implements Serializable {

    private static final long serialVersionUID = 8657200925423295372L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    // 保持幂等性的字段
    //private String ticket;

    // 信用额度
    private long creditMoney;

    // 赠送金额
    private long presentMoney;

    // 余额 废弃掉
    private long remainingMoney;

    // 负债金额
    private long debtMoney;

    private Date modifyTime;

    private Date createTime;

}
