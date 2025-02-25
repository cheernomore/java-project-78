package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class MapSchema<T> extends BaseSchema<Map<String, T>> {

    private int size = 0;
    private boolean isRequired;
    private final Map<String, BaseSchema<T>> shapes = new HashMap<>();
    public MapSchema() {
        super();
    }

    private final Predicate<Map<String, T>> required = map -> !isRequired || (map != null && !map.isEmpty());

    private final Predicate<Map<String, T>> checkSize = map -> {
        HashMap<String, String> resMap = (HashMap) map;
        return resMap.size() >= size;
    };

    private final Predicate<Map<String, T>> checkShape = shape ->
            shapes.entrySet().stream()
                    .allMatch(entry -> {
                        String key = entry.getKey();
                        BaseSchema<T> schema = entry.getValue();
                        return schema.isValid(shape.get(key));
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

    public final MapSchema shape(Map<String, BaseSchema<T>> shape) {
        shape.forEach(shapes::putIfAbsent);
        addCheck(CheckName.CHECK_SHAPE, checkShape);
        return this;
    }
}
