package hexlet.code.schemas;

import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@NoArgsConstructor
public class StringSchema<T> extends BaseSchema<T> {
    private boolean isRequired = false;
    private int minLength;
    private final Set<String> containsSet = new HashSet<>();

    private final Predicate<Object> requiredCheck = number -> this.isRequired && number != null && !number.equals("");
    private final Predicate<Object> minLengthCheck = str -> str.toString().length() >= minLength;
    private final Predicate<Object> containsCheck = str -> {
        for (String entry : containsSet) {
            if (str.toString().contains(entry)) {
                return false;
            }
        }
        return true;
    };

    public final StringSchema required() {
        this.isRequired = true;
        checks.put(CheckName.IS_REQUIRED, requiredCheck);
        return this;
    }

    public final StringSchema minLength(int minLengthValue) {
        this.minLength = minLengthValue;
        checks.put(CheckName.CHECK_MIN_LENGTH, minLengthCheck);
        return this;
    }

    public final StringSchema contains(String text) {
        this.containsSet.add(text);
        checks.put(CheckName.CHECK_CONTAINS, containsCheck);
        return this;
    }
}
