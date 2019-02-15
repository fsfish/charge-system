package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "config")
@Getter
@Setter
public class Config implements Serializable {

    private static final long serialVersionUID = 5424390508881325028L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //机构id
    private Long agencyId;

    //设备id
    private Long deviceId;

    //配置名
    private String name;

    //配置名对应的配置值
    private String value;
}
