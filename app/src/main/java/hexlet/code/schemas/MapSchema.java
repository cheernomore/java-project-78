package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class MapSchema<T> extends BaseSchema {

    private int size = 0;
    private boolean isRequired;

    private Map<String, BaseSchema> shapes = new HashMap<>();

    public MapSchema() {
        super();
    }

    private final Predicate<Object> required = Objects::isNull;
    private final Predicate<Object> checkSize = size -> (int) size > this.size;
    private final Predicate<Object> checkShape = shape -> shape instanceof Map<?,?>;

    public final MapSchema<T> required() {
        this.isRequired = true;
        addCheck(CheckName.IS_REQUIRED, required);
        return this;
    }

    public final MapSchema<T> sizeof(int setSize) {
        this.size = setSize;
        addCheck(CheckName.CHECK_SIZE, checkSize);
        return this;
    }

    public final MapSchema<T> shape(Map<String, BaseSchema> shape) {
        this.shapes = shape;
        addCheck(CheckName.CHECK_SHAPE, checkShape);
        return this;
    }
}
