package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class MapSchema extends BaseSchema<Map<String, String>> {
    private int size = 0;
    private final Map<String, BaseSchema<?>> shapes = new HashMap<>();
    public MapSchema() {
        super();
    }

    private final Predicate<Map<String, String>> isRequiredAndNotNull =
            map -> required && (map != null);
    private final Predicate<Map<String, String>> checkSize = map -> map.size() == size;
    private final Predicate<Map<String, String>> checkShape = shape ->
            shapes.entrySet().stream()
                    .allMatch(entry -> {
                        String key = entry.getKey();
                        BaseSchema schema = entry.getValue();
                        return schema.isValid(shape.get(key));
                    });

    public final MapSchema required() {
        required = true;
        addCheck(CheckName.IS_REQUIRED, isRequiredAndNotNull);
        return this;
    }

    public final MapSchema sizeof(int setSize) {
        this.size = setSize;
        addCheck(CheckName.CHECK_SIZE, checkSize);
        return this;
    }

    public final MapSchema shape(Map<String, BaseSchema<String>> shape) {
        shape.forEach(shapes::putIfAbsent);
        addCheck(CheckName.CHECK_SHAPE, checkShape);
        return this;
    }
}
