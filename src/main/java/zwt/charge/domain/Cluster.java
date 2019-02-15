package zwt.charge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: lilongzhou
 * @Description: 集群信息
 * @Date: Created in 16:30 2018/8/16
 */
@Entity
@Table(name = "cluster")
@Setter
@Getter
public class Cluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    private String port;

    @Column(columnDefinition = "tinyint(4) default 0")
    private boolean deleted = false;

}
