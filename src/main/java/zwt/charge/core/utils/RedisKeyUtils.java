package zwt.charge.core.utils;

import zwt.charge.core.Constant;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午3:57 2018/12/28
 */
public class RedisKeyUtils {

    /**
     * 与设备alias相关的设备列表
     * @param deviceAlias 设备alias
     * @return
     */
    public static String deviceAliasKey(String deviceAlias) {
        return "device_alias:" + deviceAlias;
    }

    /**
     * 与设备订阅流相关的订阅流列表
     * @param deviceStream
     * @return
     */
    public static String deviceStreamKey(String deviceStream) {
        return "device_stream:" + deviceStream;
    }

    /**
     * 正在计费的key
     * @return
     */
    public static String consumptionKey() {
        return Constant.CONSUMPTION_KEY;
    }

    /**
     * 正在被课程使用的设备 key
     * @param deviceAlias 设备alias
     * @return
     */
    public static String deviceCoursing(String deviceAlias) {
        return "device_coursing:" + deviceAlias;
    }

    /**
     * 与机构余额有关的信息 hash
     * @param agencyId
     * @return
     */
    public static String chargeAgencyBalanceKey(Long agencyId) {
        return "charge_agency_balance:" + agencyId;
    }

    public static String chargeAgencyhashKey(String hashKey) {
        return hashKey + ":" + hashKey;
    }

}
