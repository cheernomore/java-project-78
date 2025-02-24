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
    private final Predicate<Integer> requiredCheck = number -> this.isRequired && number != null;
    private final Predicate<Integer> positiveCheck = num -> num > 0;
    private final Predicate<Integer> rangeCheck = number -> number >= this.from && number <= this.to;

    public final NumberSchema required() {
        this.isRequired = true;
        checks.put(CheckName.IS_REQUIRED, requiredCheck);
        return this;
    }

    public final NumberSchema positive() {
        this.isPositive = true;
        checks.put(CheckName.CHECK_POSITIVE, positiveCheck);
        return this;
    }

    public final NumberSchema range(int fromValue, int toValue) {
        this.from = fromValue;
        this.to = toValue;
        checks.put(CheckName.CHECK_RANGE, rangeCheck);
        return this;
    }
}
