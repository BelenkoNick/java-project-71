package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DifferYamlTest {

    @Test
    void testGenerateWithIdenticalYamlFiles() throws Exception {
        String filePath1 = "src/test/resources/identical1.yaml";
        String filePath2 = "src/test/resources/identical2.yaml";

        String expected = "{\n"
                + "    host: hexlet.io\n"
                + "    timeout: 50\n"
                + "}";

        String result = Differ.generate(filePath1, filePath2);
        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithDifferentYamlFiles() throws Exception {
        String filePath1 = "src/test/resources/different1.yaml";
        String filePath2 = "src/test/resources/different2.yaml";

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
    void testGenerateWithMissingYamlFile() {
        String filePath1 = "src/test/resources/existing.yaml";
        String filePath2 = "src/test/resources/non_existing.yaml";

        Exception exception = assertThrows(Exception.class, () -> {
            Differ.generate(filePath1, filePath2);
        });

        String expectedMessage = " does not exist.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
