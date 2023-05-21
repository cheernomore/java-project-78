package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    @Test
    void checkValidStringInputs() {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();
        String testLessThanLength10 = "Score!";
        int minLength = 10;
        String sentenceToCheckContainsWords = "The Sun is rising. My shadow is getting shower";
        String stopWord = "Sun";
        String emptyString = "";

        assertThat(stringSchema.isValid(null)).isEqualTo(true);
        assertThat(stringSchema.isValid(emptyString)).isEqualTo(true);
        stringSchema.required();
        assertThat(stringSchema.isValid(null)).isEqualTo(false);
        assertThat(stringSchema.isValid(emptyString)).isEqualTo(false);

        assertThat(stringSchema.isValid(testLessThanLength10)).isEqualTo(true);
        stringSchema.minLength(minLength);
        assertThat(stringSchema.isValid(testLessThanLength10)).isEqualTo(false);

        assertThat(stringSchema.isValid(sentenceToCheckContainsWords)).isEqualTo(true);
        stringSchema.contains(stopWord);
        assertThat(stringSchema.isValid(sentenceToCheckContainsWords)).isEqualTo(false);
    }

    @Test
    void checkValidIntegerInputs(){
        Validator validator = new Validator();
        NumberSchema numberSchema = validator.number();
        Range range = new Range(10, 29);

        assertThat(numberSchema.isValid(null)).isEqualTo(true);
        numberSchema.required();
        assertThat(numberSchema.isValid(null)).isEqualTo(false);

        assertThat(numberSchema.isValid(-10)).isEqualTo(true);
        numberSchema.positive();
        assertThat(numberSchema.isValid(-10)).isEqualTo(false);

        assertThat(numberSchema.isValid(30)).isEqualTo(true);
        numberSchema.range(range);
        assertThat(numberSchema.isValid(30)).isEqualTo(false);
    }

    @Test
    void checkValidMapInputs() {
        Validator v = new Validator();

        MapSchema schema = v.map();

        schema.isValid(null); // true

        schema.required();

        schema.isValid(null); // false
        schema.isValid(new HashMap()); // true
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        schema.isValid(data); // true

        schema.sizeof(2);

        schema.isValid(data);  // false
        data.put("key2", "value2");
        schema.isValid(data); // true
    }
}
