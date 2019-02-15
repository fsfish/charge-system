package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lilongzhou
 * @Description: 可登陆网站的用户--教师用
 * @Date: Created in 下午12:03 2018/9/3
 */
@Table(name = "account")
@Entity
@Getter
@Setter
public class Account implements Serializable {

    private static final long serialVersionUID = 7377876934779997473L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    // 设备id
    private Long deviceId;

    //用户名
    @Column(unique = true)
    private String username;

    //密码
    @Column(unique = true)
    private String passwd;

    //手机号 --- 老师手机号
    private String mobile;

    private Date createTime;

    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean disabled = false;

    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean deleted = false;

}
