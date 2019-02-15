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

/**
 * client_credentials oauth认证
 * 机构调用api时需要传递的client_id、client_secret
 */
@Entity
@Table(name = "agency_app")
@Getter
@Setter
public class AgencyApp implements Serializable {

    private static final long serialVersionUID = 2832600445147005028L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //机构id
    private Long agencyId;

    //用于认证的client_id
    @Column(unique = true)
    private String clientId;

    //用户认证的client_secret
    private String clientSecret;

    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean deleted = false;
}
