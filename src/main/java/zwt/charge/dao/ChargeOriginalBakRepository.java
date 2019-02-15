package zwt.charge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import zwt.charge.domain.ChargeOriginalBak;

/**
 * @Author: lilongzhou
 * @Description: 数据备份持久层mongo
 * @Date: Created in 下午5:11 2018/12/24
 */
public interface ChargeOriginalBakRepository extends MongoRepository<ChargeOriginalBak, String> {

}
