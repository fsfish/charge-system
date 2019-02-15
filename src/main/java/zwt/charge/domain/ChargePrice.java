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
 * @Description: 计费价格表
 * @Date: Created in 下午1:14 2019/1/4
 */
@Table(name = "charge_price")
@Entity
@Setter
@Getter
public class ChargePrice implements Serializable {

    private static final long serialVersionUID = 2114737593604616912L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    // 直播定价 暂时使用此定价 分为单位
    private long price;

    // 视频收费标准
    private long videoPrice;

    // 音频收费标准
    private long audioPrice;

    // 打折费用
    private long discount;

    private Date createTime;

    private Date modifyTime;

}
