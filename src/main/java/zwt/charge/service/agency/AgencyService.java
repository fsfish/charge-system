package zwt.charge.service.agency;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zwt.charge.core.utils.BeanCopyUtils;
import zwt.charge.dao.AgencyRepository;
import zwt.charge.dao.ChargePriceRepository;
import zwt.charge.domain.Agency;
import zwt.charge.domain.ChargePrice;
import zwt.charge.domain.QAgency;
import zwt.charge.domain.QChargePrice;
import zwt.charge.service.vo.AgencyVO;
import zwt.charge.service.vo.BillingVO;

import java.util.Optional;

/**
 * @Author: lilongzhou
 * @Description: 机构业务层
 * @Date: Created in 下午2:32 2019/1/23
 */
@Service
@Slf4j
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private ChargePriceRepository chargePriceRepository;

    public AgencyVO getAgencyById(Long agencyId) {
        AgencyVO agencyVO = new AgencyVO();
        QAgency qagency = QAgency.agency;
        Predicate predicate = qagency.id.eq(agencyId);
        Optional<Agency> agencyOptional = agencyRepository.findOne(predicate);
        if (agencyOptional.isPresent()) {
            agencyVO = BeanCopyUtils.copy(agencyOptional.get(), AgencyVO.class);
        }
        return agencyVO;
    }

    public BillingVO getAgencyBilling(Long agencyId) {
        BillingVO billingVO = new BillingVO();
        QChargePrice qchargePrice = QChargePrice.chargePrice;
        Predicate predicate = qchargePrice.agencyId.eq(agencyId);
        Optional<ChargePrice> optional = chargePriceRepository.findOne(predicate);
        if (optional.isPresent()) {
            ChargePrice chargePrice = optional.get();
            billingVO = BeanCopyUtils.copy(chargePrice, BillingVO.class);
        }
        return billingVO;
    }


}
