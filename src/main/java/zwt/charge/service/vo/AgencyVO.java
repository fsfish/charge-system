package zwt.charge.service.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AgencyVO {

    private Long id;

    private String name;

    private String description;

    private Date createTime;

    private boolean deleted = false;

    private String remark;

    private String mobile;

    private String contactName;

    // 管理员账号
    private String adminAccount;

    // 管理员账号密码
    private String adminPassword;

}
