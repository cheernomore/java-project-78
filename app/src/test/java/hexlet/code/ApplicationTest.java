package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private final Validator validator = new Validator();
    private final StringSchema stringSchema = validator.string();
    private final NumberSchema numberSchema = validator.number();
    private final MapSchema mapSchema = validator.map();

    @Test
    @DisplayName("Строка: проверка минимальной длины")
    public void testStringMinLength() {
        assertThat(stringSchema.isValid("Short")).isTrue();
        stringSchema.minLength(10);
        assertThat(stringSchema.isValid("Short")).isFalse();
        assertThat(stringSchema.isValid("Longer than ten")).isTrue();
    }

    @Test
    @DisplayName("Строка: проверка обязательного заполнения")
    public void testStringRequired() {
        assertThat(stringSchema.isValid("")).isTrue();
        stringSchema.required();
        assertThat(stringSchema.isValid("")).isFalse();
    }

    @Test
    @DisplayName("Строка: проверка вхождения подстроки")
    public void testStringContains() {
        stringSchema.contains("hello");
        assertThat(stringSchema.isValid("hello world")).isTrue();
        assertThat(stringSchema.isValid("world")).isFalse();
    }

    @Test
    @DisplayName("Число: проверка обязательного заполнения")
    public void numberRequiredTest() {
        assertTrue(numberSchema.isValid(null));
        numberSchema.required();
        assertFalse(numberSchema.isValid(null));
    }

    @Test
    @DisplayName("Число: проверка позитивных числе")
    public void positiveNumberTest() {
        final int negativeTestValue = -1;

        assertTrue(numberSchema.isValid(negativeTestValue));
        numberSchema.positive();
        assertFalse(numberSchema.isValid(negativeTestValue));
    }

    @Test
    @DisplayName("Число: проверка вхождения в диапазон")
    public void inRangeTest() {
        final int testValue = 10;
        final int negativeTestValue = 20;
        final int from = 9;
        final int to = 11;

        assertTrue(numberSchema.isValid(testValue));
        numberSchema.range(from, to);
        assertTrue(numberSchema.isValid(testValue));
        assertFalse(numberSchema.isValid(negativeTestValue));
    }

    @Test
    @DisplayName("Map: проверка ограничения по размеру и обязательность")
    public void mapSizeTest() {
        final int mapSize = 1;

        final Map<String, Object> emptyMap = Map.of();
        final Map<String, Object> testMap = Map.of("firstName", "Ever", "lastname", "greatest");

        assertTrue(mapSchema.isValid(emptyMap));
        mapSchema.required();
        assertFalse(mapSchema.isValid(emptyMap));

        assertTrue(mapSchema.isValid(testMap));
        mapSchema.sizeOf(mapSize);
        assertFalse(mapSchema.isValid(testMap));
    }

    @Test
    @DisplayName("Map: проверка вложенных схем")
    public void shapeMapTest() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();

        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        mapSchema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(mapSchema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(mapSchema.isValid(human2));

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(mapSchema.isValid(human3));

    }
}
