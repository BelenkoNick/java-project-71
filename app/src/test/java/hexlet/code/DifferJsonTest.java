package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DifferJsonTest {

    @Test
    void testGenerateWithIdenticalFiles() throws Exception {
        String filePath1 = "src/test/resources/identical1.json";
        String filePath2 = "src/test/resources/identical2.json";

        String expected = "{\n"
                + "    host: hexlet.io\n"
                + "    timeout: 50\n"
                + "}";

        String result = Differ.generate(filePath1, filePath2);
        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithDifferentFiles() throws Exception {
        String filePath1 = "src/test/resources/different1.json";
        String filePath2 = "src/test/resources/different2.json";

        String expected = "{\n"
                + "  - follow: false\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true\n"
                + "}";

        String result = Differ.generate(filePath1, filePath2);
        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithMissingFile() {
        String filePath1 = "src/test/resources/existing.json";
        String filePath2 = "src/test/resources/non_existing.json";

        Exception exception = assertThrows(Exception.class, () -> {
            Differ.generate(filePath1, filePath2);
        });

        String expectedMessage = " does not exist.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

