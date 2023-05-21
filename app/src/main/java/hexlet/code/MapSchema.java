package hexlet.code;

import java.util.Map;

public class MapSchema extends BaseSchema {

    private boolean isRequired = false;
    private int size;
    public MapSchema() {
    }

    public MapSchema required() {
        this.isRequired = true;
        return this;
    }

    public MapSchema sizeof(int size) {
        this.size = size;
        return this;
    }

    @Override
    boolean isValid(Object input) {

        if (this.isRequired && input == null) {
            return false;
        } else if (!this.isRequired && input == null) {
            return true;
        }

        if (input instanceof Map) {
            Map<?, ?> message = (Map<?, ?>) input;

            for (Map.Entry<?, ?> entry : message.entrySet()) {
                if (!(entry.getKey() instanceof String) || !(entry.getValue() instanceof String)) {
                    return false;
                }
            }

            if (message.size() < this.size) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }
}
