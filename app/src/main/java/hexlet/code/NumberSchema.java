package hexlet.code;

public class NumberSchema extends BaseSchema {

    private boolean isRequired = false;
    private boolean isPositive = false;
    private Range rangeBetween;
    public NumberSchema() {
    }

    public NumberSchema required() {
        this.isRequired = true;
        return this;
    }

    public NumberSchema positive() {
        this.isPositive = true;
        return this;
    }

    public NumberSchema range(Range range) {
        this.rangeBetween = range;
        return this;
    }

    @Override
    boolean isValid(Object input) {
        Integer number = (Integer) input;

        if (this.isRequired && (number == null)) {
            return false;
        }

        if (!this.isRequired && (number == null)) {
            return true;
        }

        if (this.isPositive && (number < 0)) {
            return false;
        }


        if (rangeBetween != null) {
            if (number < this.rangeBetween.getNumFrom() || number > this.rangeBetween.getNumTo()) {
                return false;
            }
        }

        return true;
    }
}
