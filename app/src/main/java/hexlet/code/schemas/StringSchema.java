package hexlet.code.schemas;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class StringSchema extends BaseSchema<String> {
    private boolean isRequired = false;
    private int minLength = 0;
    private final Map<String, String> containsMap = new HashMap<>();

    public StringSchema required() {
        this.isRequired = true;
        return this;
    }

    public StringSchema minLength(int minLengthValue) {
        this.minLength = minLengthValue;
        return this;
    }

    public StringSchema contains(String text) {
        this.containsMap.putIfAbsent(text, text);
        return this;
    }

    @Override
    public boolean isValid(String input) {
        if (isRequired && (input == null || input.isEmpty())) {
            return false;
        }

        if (minLength > 0 && (input == null || input.length() < minLength)) {
            return false;
        }

        for (Map.Entry<String, String> entry : containsMap.entrySet()) {
            if (!input.contains(entry.getValue())) {
                return false;
            }
        }

        return true;
    }
}
