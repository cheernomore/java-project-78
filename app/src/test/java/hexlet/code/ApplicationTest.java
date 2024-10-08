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

public class ApplicationTest {
    @Test
    @DisplayName("Проверка проверяемости длины строки")
    public void validVerifyStringLength() {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();
        String testMoreThan10Char = "We come for you. No chances";
        String testLessThan10Char = "We";
        int minLength = 10;

        assertThat(stringSchema.isValid(testLessThan10Char)).isEqualTo(true);
        stringSchema.minLength(minLength);
        assertThat(stringSchema.isValid(testMoreThan10Char)).isEqualTo(true);
        assertThat(stringSchema.isValid(testLessThan10Char)).isEqualTo(false);
    }

    @Test
    @DisplayName("Проверка проверяемости строки на заполненность")
    public void validVerifyStringRequired() {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();
        String testMoreThan10Char = "";

        assertThat(stringSchema.isValid(testMoreThan10Char)).isEqualTo(true);
        stringSchema.required();
        assertThat(stringSchema.isValid(testMoreThan10Char)).isEqualTo(false);
    }

    @Test
    @DisplayName("Проверка проверяемости строки на вхождение")
    public void validVerifyStringStopWord() {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();
        String sentenceWithStopWord = "We come for you. No chances";
        String sentenceWithoutStopWord = "We for you. No chances";

        assertThat(stringSchema.isValid(sentenceWithoutStopWord)).isEqualTo(true);
        stringSchema.contains("come");
        assertThat(stringSchema.isValid(sentenceWithoutStopWord)).isEqualTo(false);
        assertThat(stringSchema.isValid(sentenceWithStopWord)).isEqualTo(true);
    }

    @Test
    @DisplayName("Проверка на длину строки и на вхождение")
    public void fullVerifyStringValidator() {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();
        String sentenceWithStopWord = "We come for you. No chances";
        String sentenceWithoutStopWord = "We for you. No chances";

        assertThat(stringSchema.isValid(sentenceWithoutStopWord)).isEqualTo(true);
        stringSchema.contains("come");
        assertThat(stringSchema.isValid(sentenceWithoutStopWord)).isEqualTo(false);
        assertThat(stringSchema.isValid(sentenceWithStopWord)).isEqualTo(true);

        assertThat(stringSchema.isValid(sentenceWithStopWord)).isEqualTo(true);
        stringSchema.minLength(50);
        assertThat(stringSchema.isValid(sentenceWithStopWord)).isEqualTo(false);
    }

    @Test
    @DisplayName("Набор проверок для валидатора чисел")
    public void checkValidIntegerInputs() {
        Validator validator = new Validator();
        NumberSchema numberSchema = validator.number();

        assertThat(numberSchema.isValid(null)).isEqualTo(true);
        numberSchema.required();
        assertThat(numberSchema.isValid(null)).isEqualTo(false);

        assertThat(numberSchema.isValid(-10)).isEqualTo(true);
        numberSchema.positive();
        assertThat(numberSchema.isValid(-10)).isEqualTo(false);

        assertThat(numberSchema.isValid(30)).isEqualTo(true);
        numberSchema.range(10, 29);
        assertThat(numberSchema.isValid(30)).isEqualTo(false);
    }

    @Test
    @DisplayName("Набор проверок для валидатора Map")
    public void checkValidMapInputs() {
        Validator v = new Validator();

        MapSchema schema = v.map();

        schema.isValid(null); // true

        schema.required();

        schema.isValid(null); // false
        schema.isValid(new HashMap<>()); // true
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        schema.isValid(data); // true

        schema.sizeof(2);

        schema.isValid(data);  // false
        data.put("key2", "value2");
        schema.isValid(data); // true
    }

    @Test
    @DisplayName("Набор проверок для валидатора NestedMap")
    public void checkValidMapInputsWithNested() {
        Validator v = new Validator();
        MapSchema schema = v.map();
        Map<String, BaseSchema<String>> schemas = new HashMap<>();

        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 100);
        schema.isValid(human1); // true

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        schema.isValid(human2); // true

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        schema.isValid(human3); // false

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", -5);
        schema.isValid(human4); // false
    }
}
