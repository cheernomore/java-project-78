package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class MapSchema<T> extends BaseSchema<T> {

    private int size = 0;
    private boolean isRequired;

    private Map<String, BaseSchema<T>> shapes = new HashMap<>();

    public MapSchema() {
        super();
    }

    private final Predicate<Object> required = Objects::isNull;
    private final Predicate<Object> checkSize = cs -> {
        Map<String, String> gr = (Map<String, String>) cs;
        return !gr.isEmpty();
    };
    private final Predicate<Object> checkShape = shape -> {
        if (shape instanceof Map<?, ?> message) {
            for (Map.Entry<?, ?> entry : message.entrySet()) {
                if (!(entry.getKey() instanceof String) || !(entry.getValue() instanceof String)) {
                    return false;
                }
            }
        } else {
            Map<?, ?> message = (Map<?, ?>) shape;

            for (String key : shapes.keySet()) {
                if (!message.containsKey(key)) {
                    continue;
                }

                BaseSchema<T> schema = shapes.get(key);

                if (!schema.isValid((T) message.get(key))) {
                    return false;
                }
            }
        }

        return true;
    };

    public final MapSchema<T> required() {
        this.isRequired = true;
        addCheck(CheckName.IS_REQUIRED, required);
        return this;
    }

    public final MapSchema<T> sizeof(int setSize) {
        this.size = setSize;
        addCheck(CheckName.CHECK_SIZE, checkSize);
        return this;
    }

    public final MapSchema<T> shape(Map<String, BaseSchema<T>> shape) {
        this.shapes = shape;
        addCheck(CheckName.CHECK_SHAPE, checkShape);
        return this;
    }
}
