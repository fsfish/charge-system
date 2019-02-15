package zwt.charge.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.stream.Collectors;

public class BeanMapUtils {

    private static ObjectMapper objectMapper;

    public static ObjectMapper build() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            //objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        }
        return objectMapper;
    }

    public static <T> Map<String, Object> buildMap(T t) {
        Map<String, Object> map = build().convertValue(t, new TypeReference<Map<String, Object>>(){});
        return map.entrySet().stream()
                .filter(stringObjectEntry -> stringObjectEntry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public static <T> Map<String, String> buildStringMap(T t) {
        Map<String, String> stringMap = build().convertValue(t, new TypeReference<Map<String, String>>(){});
        return stringMap.entrySet().stream()
                .filter(stringEntry -> stringEntry.getValue() != null && !stringEntry.getValue().equals("null"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
