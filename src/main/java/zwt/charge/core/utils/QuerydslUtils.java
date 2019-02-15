package zwt.charge.core.utils;

import com.querydsl.core.types.dsl.BooleanExpression;

public class QuerydslUtils {

    public static String buildLikeParam(String content) {
        return "%" + content + "%";
    }

    public static BooleanExpression and(BooleanExpression before, BooleanExpression after) {
        if (before == null) {
            return after;
        }
        return before.and(after);
    }
}
