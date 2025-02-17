package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class MapSchema extends BaseSchema {

    private int size = 0;
    private boolean isRequired;

    private final Map<String, BaseSchema> shapes = new HashMap<>();

    public MapSchema() {
        super();
    }

    private final Predicate<Object> required = Objects::isNull;
    private final Predicate<Object> checkSize = cs -> {
        Map<String, String> gr = (Map<String, String>) cs;
        return !gr.isEmpty();
    };

    private final Predicate<Object> checkShape = shape -> shape instanceof MapSchema;

    public final MapSchema required() {
        this.isRequired = true;
        addCheck(CheckName.IS_REQUIRED, required);
        return this;
    }

    public final MapSchema sizeof(int setSize) {
        this.size = setSize;
        addCheck(CheckName.CHECK_SIZE, checkSize);
        return this;
    }

    public final MapSchema shape(Map<String, BaseSchema> shape) {
        shape.forEach(shapes::putIfAbsent);
        addCheck(CheckName.CHECK_SHAPE, checkShape);
        return this;
    }
}
