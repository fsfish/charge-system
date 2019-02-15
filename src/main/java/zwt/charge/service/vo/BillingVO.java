package zwt.charge.service.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午2:57 2019/1/7
 */
@Setter
@Getter
public class BillingVO {

    private Long id;

    private Long agencyId;

    // 直播定价 暂时使用此定价
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
