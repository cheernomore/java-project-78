package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class MapSchema extends BaseSchema {

    private int size = 0;
    private boolean isRequired;
    private final Map<String, BaseSchema> shapes = new HashMap<>();
    public MapSchema() {
        super();
    }

    private final Predicate<Object> required = map ->
            !isRequired || (map instanceof Map<?, ?> && !((Map<?, ?>) map).isEmpty());

    private final Predicate<Object> checkSize = map -> {
        HashMap<String, String> resMap = (HashMap) map;
        return resMap.size() >= size;
    };

    private final Predicate<Object> checkShape = shape ->
            shape instanceof Map<?, ?> && shapes.entrySet().stream()
                    .allMatch(entry -> {
                        String key = entry.getKey();
                        BaseSchema schema = entry.getValue();
                        return schema.isValid(((Map<?, ?>) shape).get(key));
                    });

    public final MapSchema required() {
        this.isRequired = true;
        addCheck(CheckName.IS_REQUIRED, required);
        return this;
    }

    public final MapSchema sizeof(int setSize) {
        this.size = setSize;
        addCheck(CheckName.CHECK_SIZE, checkSize);
        return this;
    }

    public final MapSchema shape(Map<String, BaseSchema> shape) {
        shape.forEach(shapes::putIfAbsent);
        addCheck(CheckName.CHECK_SHAPE, checkShape);
        return this;
    }
}
