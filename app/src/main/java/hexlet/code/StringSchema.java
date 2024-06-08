package hexlet.code;

import java.util.HashMap;
import java.util.Map;

public class StringSchema extends BaseSchema {
    private boolean isRequired = false;
    private int hasMinLength = 0;
    private final Map<String, String> isContains = new HashMap<>();

    public StringSchema() {
    }

    public StringSchema required() {
        this.isRequired = true;
        return this;
    }

    public StringSchema minLength(int minLength) {
        this.hasMinLength = minLength;
        return this;
    }

    public StringSchema contains(String text) {
        this.isContains.putIfAbsent(text, text);
        return this;
    }

    @Override
    boolean isValid(Object input) {
        String message = (String) input;

        if (this.isRequired && (message == null || message.isEmpty())) {
            return false;
        }

        if (this.hasMinLength > 0 && message.length() < this.hasMinLength) {
            return false;
        }

        for (Map.Entry<String, String> entry: isContains.entrySet()) {
            return message.contains(entry.getValue());
        }

        return true;
    }
}
