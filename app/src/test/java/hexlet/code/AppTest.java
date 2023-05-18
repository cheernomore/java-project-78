package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    @Test
    void checkValidValidations() {
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
}
