package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class MapSchema<T> extends BaseSchema<T> {

    private int size = 0;
    private boolean isRequired;

    private Map<String, BaseSchema<T>> shapes = new HashMap<>();

    public MapSchema() {
        super();
    }

    private final Predicate<Object> required = Objects::isNull;
    private final Predicate<Object> checkSize = cs -> {
        Map<String, String> gr = (Map<String, String>) cs;
        return !gr.isEmpty();
    };
    private final Predicate<Object> checkShape = shape -> shape instanceof Map<?, ?>;

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

    public final MapSchema<T> shape(Map<String, BaseSchema<T>> shape) {
        this.shapes = shape;
        addCheck(CheckName.CHECK_SHAPE, checkShape);
        return this;
    }
}
