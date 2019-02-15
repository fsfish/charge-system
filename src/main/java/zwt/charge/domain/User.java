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
 * 可登录网站的用户
 */
@Table(name = "user")
@Entity
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 3420836065513327252L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agencyId;

    //用户名
    @Column(unique = true)
    private String username;

    private String nickname;

    //密码
    private String passwd;

    //手机号
    private String mobile;

    private Date createTime;

    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean disabled = false;

    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean manager = false;

    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean deleted = false;

    // 角色id， 1 教师 2 助教 4 机构管理员 8 超级管理员
    private int roleId;

}
