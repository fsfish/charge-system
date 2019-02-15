package zwt.charge.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

public class GsonUtils {

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm")
            .serializeNulls().create();

    private static JsonParser jsonParser = new JsonParser();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static Object fromJson(String jsonContent, Type type) {
        return gson.fromJson(jsonContent, type);
    }

    public static <T> T fromJson(String jsonContent, Class<T> clazz) {
        return gson.fromJson(jsonContent, clazz);
    }

    public static JsonObject parseToJson(String jsonContent) {
        return jsonParser.parse(jsonContent).getAsJsonObject();
    }
}
