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
 * @Description: 计费充值表
 * @Date: Created in 下午1:16 2019/1/4
 */
@Table(name = "charge_recharge")
@Entity
@Setter
@Getter
public class ChargeReCharge implements Serializable {

    private static final long serialVersionUID = -4060333772073282319L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    // 保持幂等性的字段
    //private String ticket;

    // 充值金额
    private long recharge;

    // 赠送金额
    private long presentMoney;

    // 信用额度
    private long creditMoney;

    // 充值类型
    private Integer rechargeType;

    // 备注
    private String remark;

    // 充值操作人员id
    private Long operateAccountId;

    // 充值操作人员名称
    private String operateAccountName;

    private Date modifyTime;

    private Date createTime;

    @Getter
    public enum Type {

        /**
         * 后台代充
         */
        Acting_Charge(1),

        /**
         * 自助充值
         */
        Self_Charge(2);

        private int id;

        Type(int id) {
            this.id = id;
        }
    }


}
