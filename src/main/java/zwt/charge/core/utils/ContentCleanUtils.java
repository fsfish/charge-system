package zwt.charge.core.utils;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class ContentCleanUtils {

    public static String clean(String content) {
        if(StringUtils.isBlank(content)) {
            return "";
        }

        return Jsoup.clean(content, Whitelist.basic());
    }
}
