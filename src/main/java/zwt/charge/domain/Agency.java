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

@Entity
@Table(name = "agency")
@Getter
@Setter
public class Agency implements Serializable {

    private static final long serialVersionUID = -2601587674718728088L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //机构名
    private String name;

    //描述
    private String description;

    //创建时间
    private Date createTime;

    //是否删除
    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean deleted = false;

    //备注
    private String remark;

    //手机号
    private String mobile;

    //联系人姓名
    private String contactName;

}
