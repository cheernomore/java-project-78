package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected Map<CheckName, Predicate<T>> checks = new LinkedHashMap<>();

    protected final void addCheck(CheckName checkName, Predicate<T> predicate) {
        this.checks.putIfAbsent(checkName, predicate);
    }

    public final boolean isValid(T value) {
        return checks.entrySet()
                .stream()
                .allMatch(entry -> entry.getValue().test(value));
    }
}
