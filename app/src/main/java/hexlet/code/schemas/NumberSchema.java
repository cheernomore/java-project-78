package hexlet.code.schemas;

import java.util.function.Predicate;

public class NumberSchema extends BaseSchema<Integer> {
    private boolean isRequired;
    private boolean isPositive;
    private int from;
    private int to;

    public NumberSchema() {
        super();
    }
    private final Predicate<Integer> isRequiredAndNotNull = number -> this.isRequired && number != null;
    private final Predicate<Integer> isPositiveAndGreaterThanZero = num -> isPositive && num > 0;
    private final Predicate<Integer> inRange = this::isInRange;

    public final NumberSchema required() {
        this.isRequired = true;
        checks.put(CheckName.IS_REQUIRED, isRequiredAndNotNull);
        return this;
    }

    public final NumberSchema positive() {
        this.isPositive = true;
        checks.put(CheckName.CHECK_POSITIVE, isPositiveAndGreaterThanZero);
        return this;
    }

    public final NumberSchema range(int fromValue, int toValue) {
        this.from = fromValue;
        this.to = toValue;
        checks.put(CheckName.CHECK_RANGE, inRange);
        return this;
    }

    public final boolean isInRange(int num) {
        return num >= from && num <= to;
    }
}
