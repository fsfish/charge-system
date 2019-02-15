package zwt.charge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zwt.charge.dao.ChargeOriginalBakRepository;
import zwt.charge.domain.ChargeOriginalBak;

import java.util.Date;
import java.util.List;

/**
 * @Author: lilongzhou
 * @Description: 备份数据
 * @Date: Created in 下午3:33 2018/12/20
 */
@Service
@Slf4j
public class BackupsService {

    @Autowired
    private ChargeOriginalBakRepository chargeOriginBakRepository;

    /**
     * 数据备份
     * @param chargeOriginalBak 需备份数据
     * @return 返回备份状态 true备份成功 false备份失败
     */
    public boolean backups(ChargeOriginalBak chargeOriginalBak) {
        // mongodb进行数据存储
        chargeOriginalBak.setCreateTime(new Date());
        ChargeOriginalBak chargeOriginal = chargeOriginBakRepository.save(chargeOriginalBak);
        if (chargeOriginal.getDeviceId() != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 批量写入操作
     * @param chargeOriginalBaks 需备份的批量数据
     * @return
     */
    public boolean batchBackups(List<ChargeOriginalBak> chargeOriginalBaks) {
        chargeOriginBakRepository.saveAll(chargeOriginalBaks);
        return true;
    }


}
