package hexlet.code.schemas;

import lombok.NoArgsConstructor;

import java.util.function.Predicate;

@NoArgsConstructor
public class StringSchema extends BaseSchema<String> {
    private int minLength;
    private String containsWord;

    private final Predicate<String> isRequiredAndNotEmpty = str -> required && str != null && !str.isEmpty();
    private final Predicate<String> hasMinLengthAndNotNull = str -> str != null && str.length() >= minLength;
    private final Predicate<String> shouldContains = str -> str.contains(containsWord);

    public final StringSchema required() {
        addCheck(CheckName.IS_REQUIRED, isRequiredAndNotEmpty);
        required = true;
        return this;
    }

    public final StringSchema minLength(int minLengthValue) {
        addCheck(CheckName.CHECK_MIN_LENGTH, hasMinLengthAndNotNull);
        this.minLength = minLengthValue;
        return this;
    }

    public final StringSchema contains(String text) {
        addCheck(CheckName.CHECK_CONTAINS, shouldContains);
        containsWord = text;
        return this;
    }
}
