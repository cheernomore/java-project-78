package hexlet.code.schemas;

import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@NoArgsConstructor
public class StringSchema extends BaseSchema {
    private boolean isRequired = false;
    private int minLength;
    private final Set<String> containsSet = new HashSet<>();

    private final Predicate<Object> requiredCheck = str -> isRequired && str != null;  // this.isRequired && (str != null || !str.equals(""));
    private final Predicate<Object> minLengthCheck = str -> str != null && str.toString().length() >= minLength;
    private final Predicate<Object> containsCheck = str -> {
        for (String entry : containsSet) {
            if (str.toString().contains(entry)) {
                return true;
            }
        }
        return false;
    };

    public final StringSchema required() {
        addCheck(CheckName.IS_REQUIRED, requiredCheck);
        this.isRequired = true;
        return this;
    }

    public final StringSchema minLength(int minLengthValue) {
        addCheck(CheckName.CHECK_MIN_LENGTH, minLengthCheck);
        this.minLength = minLengthValue;
        return this;
    }

    public final StringSchema contains(String text) {
        addCheck(CheckName.CHECK_CONTAINS, containsCheck);
        this.containsSet.add(text);
        return this;
    }
}
