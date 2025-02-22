package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected Map<CheckName, Predicate<Object>> checks = new LinkedHashMap<>();

    protected final void addCheck(CheckName checkName, Predicate<Object> predicate) {
        this.checks.putIfAbsent(checkName, predicate);
    }

    public final boolean isValid(Object value) {
        return checks.entrySet().stream()
                .allMatch(entry -> entry.getValue().test(value));
    }
}
