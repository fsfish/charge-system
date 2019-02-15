package zwt.charge.service.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 上午11:43 2019/1/21
 */
@Setter
@Getter
public class AgencyChargeVO {

    private Long agencyId;

    private Long courseId;

    // 保持幂等性的字段
    private String ticket;

    private Integer deviceType;

    private String agencyName;

    private long audioPrice;

    private long videoPrice;

    // 直播定价 暂时使用此定价 分为单位
    private long price;

    private long flowPrice;

}
