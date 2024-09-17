package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

public class MapSchema<T> extends BaseSchema<T> {

    private boolean isRequired = false;
    private int size = 0;

    private Map<String, BaseSchema<T>> shapes = new HashMap<>();

    public MapSchema() {
    }

    public final MapSchema<T> required() {
        this.isRequired = true;
        return this;
    }

    public final MapSchema<T> sizeof(int setSize) {
        this.size = setSize;
        return this;
    }

    public final MapSchema<T> shape(Map<String, BaseSchema<T>> shape) {
        this.shapes = shape;
        return this;
    }

    @Override
    public final boolean isValid(Object input) {

        if (this.isRequired && input == null) {
            return false;
        } else if (!this.isRequired && input == null) {
            return true;
        }

        if (this.shapes.isEmpty()) {
            if (input instanceof Map<?, ?> message) {
                for (Map.Entry<?, ?> entry : message.entrySet()) {
                    if (!(entry.getKey() instanceof String) || !(entry.getValue() instanceof String)) {
                        return false;
                    }
                }

                if (this.size > 0 && message.size() < this.size) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            Map<?, ?> message = (Map<?, ?>) input;

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
    }
}
