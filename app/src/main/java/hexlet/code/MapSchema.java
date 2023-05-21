package hexlet.code;

import java.util.Map;

public class MapSchema extends BaseSchema {

    private boolean isRequired = false;
    private int size = 0;

    private Map<String, BaseSchema> shapes;

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
    boolean isValid(Object input) {

        if (this.isRequired && input == null) {
            return false;
        } else if (!this.isRequired && input == null) {
            return true;
        }

        if (this.shapes == null) {
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
            // Перебираем все ключи в shape
            for (String key : shapes.keySet()) {
                // Проверяем, есть ли такой ключ в input
                if (!message.containsKey(key)) {
                    continue;
                }

                // Если есть, то мы берем соответствующую схему для валидации
                BaseSchema schema = shapes.get(key);

                // И валидируем значение ключа с помощью этой схемы
                if (!schema.isValid(message.get(key))) {
                    return false;
                }
            }
        }

        return true;
    }
}
