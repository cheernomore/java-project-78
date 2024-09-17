package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

public class MapSchema<T> extends BaseSchema {

    private boolean isRequired = false;
    private int size = 0;

    private Map<String, BaseSchema> shapes = new HashMap<>();

    public MapSchema() {
    }

    public MapSchema required() {
        this.isRequired = true;
        return this;
    }

    public MapSchema sizeof(int setSize) {
        this.size = setSize;
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> shape) {
        this.shapes = shape;
        return this;
    }

    @Override
    public boolean isValid(Object input) {

        if (this.isRequired && input == null) {
            return false;
        } else if (!this.isRequired && input == null) {
            return true;
        }

        if (this.shapes.size() == 0) {
            if (input instanceof Map) {
                Map<?, ?> message = (Map<?, ?>) input;

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

                BaseSchema schema = shapes.get(key);

                if (!schema.isValid(message.get(key))) {
                    return false;
                }
            }
        }

        return true;
    }
}
