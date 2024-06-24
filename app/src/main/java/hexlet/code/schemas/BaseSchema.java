package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    abstract boolean isValid(T message);
}
