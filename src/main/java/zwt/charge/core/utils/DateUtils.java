package zwt.charge.core.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.sound.midi.Soundbank;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午6:36 2019/1/17
 */
public final class DateUtils {

    private static String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";

    //private static String chargePattern  = "yyyy-MM-dd HH:mm:ss";
    private static String chargePattern  = "yyyyMMddHHmmss";

    private static String lastmodifyPattern = "EEE MMM dd HH:mm:ss zzz yyyy";

    //private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    private static DateTimeFormatter dateTimeFormatters = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private DateUtils() {
    }

    // 原始字符串日期转换成日期
    public static Date dateOriToDate(String date) throws ParseException {
        return StringUtils.isEmpty(date) ? null : dateStrParse(date, getLastmodifyPattern());
    }

    //  字符串转日期
    public static Date fromString(String date) throws ParseException {
        return StringUtils.isBlank(date) ? null : parse(date, getChargePattern());
    }

    // 日期转字符串
    public static String fromDate(Date date) {
        return new SimpleDateFormat(getDatePattern()).format(date);
    }

    public static String dateSub(String date) {
        String year = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8);
        String time = date.substring(8,10) + ":" + date.substring(10, 12) + ":" + date.substring(12, 14);
        return year + " " + time;

    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds
     * @return
     */
     public static String timeStamp2Date(String seconds) {
         SimpleDateFormat sdf = new SimpleDateFormat(getChargePattern());
         //return sdf.format(new Date(Long.valueOf(seconds)));
         return sdf.format(seconds);
    }

    private static Date dateStrParse(String date, String datePattern) throws ParseException {
        return StringUtils.isEmpty(date) ? null : new SimpleDateFormat(datePattern, Locale.US).parse(date);
    }

    private static Date parse(String date, String datePattern) throws ParseException {
        return StringUtils.isBlank(date) ? null : new SimpleDateFormat(datePattern).parse(date);
    }

    public static String getDatePattern() {
        return defaultDatePattern;
    }

    public static String getLastmodifyPattern() {
        return lastmodifyPattern;
    }

    public static String getChargePattern() {
         return chargePattern;
    }

    public static void main(String[] args) throws ParseException {
        //String dateStr = "1548227781612";
        //String dateStr = "2019-01-22 22:50:17";
        //String dateStr = "20190122225017";
        //Date date = DateTime.parse(new DateTime().toDate().toString(), dateTimeFormatter).toDate();
        //String date = timeStamp2Date(dateStr);
        //Date date = fromString();
        //String date = timeStamp2Date(dateStr);
        //Date date = DateTime.parse(dateSub(dateStr), dateTimeFormatter).toDate();
        //Date d = date;
        //System.out.println(Date.parse(dateStr));
        //String date = fromDate(new Date());
        //String response = date.replaceAll("[[\\s-:punct:]]","");
        //System.out.println(response.substring(0, 12));
        String dateStr = "Thu Jan 24 11:39:20 CST 2019";
        Date date = dateOriToDate(dateStr);
        Date date1 = new DateTime(date).plusMinutes(10).toDate();
        //Date date = DateTime.parse(dateStr, dateTimeFormatters).toDate();
        System.out.println(date1);
    }

}
