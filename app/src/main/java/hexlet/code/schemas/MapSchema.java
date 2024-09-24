package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class MapSchema extends BaseSchema {

    private int size = 0;
    private boolean isRequired;

    private Map<String, BaseSchema> shapes = new HashMap<>();

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

                BaseSchema schema = shapes.get(key);

                if (!schema.isValid(message.get(key))) {
                    return false;
                }
            }
        }

        return true;
    };

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
        this.shapes = shape;
        addCheck(CheckName.CHECK_SHAPE, checkShape);
        return this;
    }
}
