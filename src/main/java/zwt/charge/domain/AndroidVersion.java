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
 * @Description:
 * @Date: Created in 上午11:35 2018/11/13
 */
@Table(name = "android_version")
@Entity
@Setter
@Getter
public class AndroidVersion implements Serializable {

    private static final long serialVersionUID = 4961694950607069549L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    // 版本号
    private Integer version;

    // 版本存储地址
    private String storagePath;

    // 版本描述
    private String description;

    // 创建时间
    private Date createTime;

    // 是否可用
    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean disabled = false;

    // 是否删除
    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean deleted = false;

}
