package zwt.charge.core.utils;

import org.joda.time.DateTime;

import java.util.Random;
import java.util.UUID;

public class UuidUtils {

    private static Random random = new Random(1000);

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generateOrder() {
        return new DateTime().toString("yyyyMMddHHmmssSSS") + buildRandomNum();
    }

    private static String buildRandomNum() {
        int randomNum = random.nextInt(1000);
        if (randomNum < 10) {
            return "00" + randomNum;
        }
        if (randomNum < 100) {
            return "0" + randomNum;
        }
        return Integer.toString(randomNum);
    }


    public static String[] getNumber(String type, int number) {
        String[] numbers = new String[number];
        String parse = "0000";
        for (int i = 0; i < number; i++) {
            numbers[i] = type + parse.substring(0, 4 - String.valueOf(i+1).length()) + (i + 1);
        }
        return numbers;
    }

    public static void main(String[] args) {
        String[] strings = getNumber("lilongzhou", 10);
        for (String s : strings) {
            System.out.println(s);
        }
    }

}
