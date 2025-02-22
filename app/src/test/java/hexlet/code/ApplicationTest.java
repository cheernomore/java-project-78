package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private Validator validator;
    private StringSchema stringSchema;
    private NumberSchema numberSchema;
    private MapSchema mapSchema;

    @BeforeEach
    void setUp() {
        validator = new Validator();
        stringSchema = validator.string();
        numberSchema = validator.number();
        mapSchema = validator.map();
    }

    @Test
    @DisplayName("Строка: проверка минимальной длины")
    public void testStringMinLength() {
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
    @DisplayName("Число: обязательность, положительность и диапазон")
    public void testNumberValidations() {
        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(0)).isTrue();

        numberSchema.required();
        assertThat(numberSchema.isValid(null)).isFalse();

        numberSchema.positive();
        assertThat(numberSchema.isValid(-10)).isFalse();
        assertThat(numberSchema.isValid(10)).isTrue();

        numberSchema.range(5, 15);
        assertThat(numberSchema.isValid(4)).isFalse();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(16)).isFalse();
    }
    @Test
    @DisplayName("Map: проверка ограничения по размеру")
    public void testMapSizeConstraint() {
        mapSchema.sizeof(2);

        Map<String, Object> emptyMap = new HashMap<>();
        Map<String, Object> oneElementMap = Map.of("key1", "value1");
        Map<String, Object> twoElementMap = Map.of("key1", "value1", "key2", "value2");
        Map<String, Object> threeElementMap = Map.of("key1", "value1", "key2", "value2", "key3", "value3");

        assertFalse(mapSchema.isValid(emptyMap));
        assertFalse(mapSchema.isValid(oneElementMap));
        assertTrue(mapSchema.isValid(twoElementMap));
        assertTrue(mapSchema.isValid(threeElementMap));
    }


    @Test
    @DisplayName("Map: проверка соответствия схемам")
    public void testMapValidation() {
        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        mapSchema.shape(schemas);

        Map<String, Object> validMap = Map.of("firstName", "John", "lastName", "Smith");

        Map<String, Object> missingRequiredField = new HashMap<>();
        missingRequiredField.put("firstName", "John");
        missingRequiredField.put("lastName", null);

        Map<String, Object> shortLastName = Map.of("firstName", "Anna", "lastName", "B");

        assertTrue(mapSchema.isValid(validMap));
        assertFalse(mapSchema.isValid(missingRequiredField));
        assertFalse(mapSchema.isValid(shortLastName));
    }

    @Test
    @DisplayName("Map: проверка вложенных структур")
    public void testNestedMapValidation() {
        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("firstname", validator.string().required());
        schemas.put("secondName", validator.string().contains("beamer"));

        mapSchema.shape(schemas);

        Map<String, Object> validMap = Map.of("firstname", "scr", "secondName", "beamer");
        Map<String, Object> invalidMap = Map.of("firstname", "scr", "secondName", "random text");

        assertTrue(mapSchema.isValid(validMap));
        assertFalse(mapSchema.isValid(invalidMap));
    }
}