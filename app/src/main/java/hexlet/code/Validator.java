package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

import java.util.Map;

public class Validator {
    public Validator() {
    }

    public final StringSchema<String> string() {
        return new StringSchema();
    }

    public final NumberSchema<Integer> number() {
        return new NumberSchema();
    }

    public final MapSchema<Map<String, String>> map() {
        return new MapSchema();
    }
}
