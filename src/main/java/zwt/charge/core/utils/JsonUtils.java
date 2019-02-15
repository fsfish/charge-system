package zwt.charge.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import zwt.charge.domain.ChargeOriginalBak;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: lilongzhou
 * @Description: json转换工具
 * @Date: Created in 下午12:44 2018/12/26
 */
@Slf4j
public class JsonUtils {

    /**
     * 将json（数组）转成list
     * @param json
     * @return
     */
    public static List<String> strToArray(String json) {
        return JSONArray.parseArray(json, String.class);
    }

    /**
     *
     * @param list
     * @return
     */
    public static JSONArray toArray(List<String> list) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return jsonArray;
    }

    /**
     * 将对象转换成json串
     * @param o 对象
     * @return 返回json串
     */
    public static String toJsonObject(Object o) {
        return JSONObject.toJSONString(o);
    }

    /**
     * json串转换成对象
     * @param json json串
     * @return 对象
     */
    public static <T> T jsonToObject(String json, Class<T> t) {
        JSONObject jsonObject = JSON.parseObject(json, new TypeReference<JSONObject>(){});
        return JSON.toJavaObject(jsonObject, t);
    }

    public static void main(String[] args) {
        ChargeOriginalBak chargeOriginalBak = new ChargeOriginalBak();
        //chargeOriginalBak.getMid("device_001");
        chargeOriginalBak.setStreamId("device_001_stream_001");
        //chargeOriginalBak.setStartTime(new Date());
        //chargeOriginalBak.setEndTime(DateTime.now().plusSeconds(10).toDate());
        chargeOriginalBak.setFlow(20);
        chargeOriginalBak.setCreateTime(new Date());


        String json = toJsonObject(chargeOriginalBak);

        List<String> list = new ArrayList<>();
        list.add(json);

        JSONArray jsonArray = toArray(list);

        log.info("jsonArray: {}", jsonArray.toJSONString());

        List<String> jsonArrays = JsonUtils.strToArray(jsonArray.toJSONString());
        jsonArrays.add(JsonUtils.toJsonObject(chargeOriginalBak));


        /*String json = toJsonObject(chargeOriginalBak);
        System.out.println(json);
        log.info("json{}", json);
        CacheDataBean cacheDataBean = jsonToObject(json, CacheDataBean.class);
        System.out.println(cacheDataBean);
        log.info("cacheDataBean{}", cacheDataBean);*/
    }




}
