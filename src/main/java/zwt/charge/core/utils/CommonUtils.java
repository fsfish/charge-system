package zwt.charge.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午2:27 18-6-13
 */
@Slf4j
public class CommonUtils {

    /**
     * 检测手机号格式
     * @param mobileNumber 手机号码
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber){
        boolean flag = false;
        try {
            // Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Pattern regex = Pattern.compile("^1[345789]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            flag = false;
        }
        return flag;
    }

}
