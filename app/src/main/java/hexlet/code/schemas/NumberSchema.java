package hexlet.code.schemas;

public class NumberSchema extends BaseSchema {

    private boolean isRequired = false;
    private boolean isPositive = false;
    private int from;
    private int to;
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

    public NumberSchema range(int fromValue, int toValue) {
        this.from = fromValue;
        this.to = toValue;
        return this;
    }

    @Override
    public boolean isValid(Object input) {
        Integer number = (Integer) input;

        if (this.isRequired && (number == null)) {
            return false;
        }

        if (!this.isRequired && (number == null)) {
            return true;
        }

        if (this.isPositive && (number <= 0)) {
            return false;
        }


        if (from != 0 && to != 0) {
            return number >= this.from && number <= this.to;
        }

        return true;
    }
}
