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
 * @Date: Created in 下午4:29 2018/8/29
 */
@Entity
@Table(name = "server")
@Setter
@Getter
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 远程服务器ip
    private String remoteIp;

    // 远程服务器port
    private String remotePort;

    // 日志服务器ip
    private String logIp;

    // 日志服务器port
    private String logPort;

    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean deleted = false;

    private Date createTime;

}
