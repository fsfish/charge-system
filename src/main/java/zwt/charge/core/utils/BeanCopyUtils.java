package zwt.charge.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

@Slf4j
public class BeanCopyUtils {

    public static <T> T copy(Object source, Class<T> clazz) {
        if(source == null) {
            throw new NullPointerException("this source is null");
        }
        BeanUtilsBean.getInstance().getConvertUtils().register(false, true, 0);
        T target;
        try {
            target = clazz.newInstance();
            BeanUtils.copyProperties(target, source);
        } catch (Throwable t) {
            log.info(t.getMessage(), t);
            throw new RuntimeException(t.getMessage(), t);
        }
        return target;
    }
}
