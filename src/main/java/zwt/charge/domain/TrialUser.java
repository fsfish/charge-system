package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 12:09 2018/8/2
 */
@Table(name = "trial_user")
@Entity
@Setter
@Getter
public class TrialUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 试用用户类型 0 免费试用用户 1 代理试用用户
     */
    private int type;

    private String userName;

    private String mobile;

    private String companyName;

    /**
     * 创建时间
     */
    private Date createTime;

    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean deleted = false;

}
