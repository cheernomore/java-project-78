package hexlet.code.schemas;

import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@NoArgsConstructor
public class StringSchema extends BaseSchema<String> {
    private boolean isRequired;
    private int minLength;
    private final Set<String> containsSet = new HashSet<>();

    private final Predicate<String> isRequiredAndNotEmpty = str -> isRequired && str != null && !str.isEmpty();
    private final Predicate<String> hasMinLengthAndNotNull = str -> str != null && str.length() >= minLength;
    private final Predicate<String> shouldContains = str -> {
        for (String entry : containsSet) {
            if (str.contains(entry)) {
                return true;
            }
        }
        return false;
    };

    public final StringSchema required() {
        addCheck(CheckName.IS_REQUIRED, isRequiredAndNotEmpty);
        this.isRequired = true;
        return this;
    }

    public final StringSchema minLength(int minLengthValue) {
        addCheck(CheckName.CHECK_MIN_LENGTH, hasMinLengthAndNotNull);
        this.minLength = minLengthValue;
        return this;
    }

    public final StringSchema contains(String text) {
        addCheck(CheckName.CHECK_CONTAINS, shouldContains);
        this.containsSet.add(text);
        return this;
    }
}
