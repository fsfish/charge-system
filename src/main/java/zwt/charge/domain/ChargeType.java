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
 * @Description: 计费类型表
 * @Date: Created in 下午1:13 2019/1/4
 */
@Table(name = "charge_type")
@Entity
@Setter
@Getter
public class ChargeType implements Serializable {

    private static final long serialVersionUID = 8857588342483227998L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    // 计费类型
    private Integer chargeType;

    private Date createTime;

    @Getter
    public enum Type {

        // 周期性收费，正常计费，一年后根据用量统一收费
        PERIOD(1),

        // 包年包月，即不管使用多少，只收取月或者年的费用
        SINGLE(2),

        // 先充值，后消费，按照使用量进行收费
        RECHARGE(3);

        private int id;

        Type(int id) {
            this.id = id;
        }
    }


}
