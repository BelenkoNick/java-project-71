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

    @Test
    void testGenerateWithComplexNestedFiles() throws Exception {
        String filePath1 = "src/test/resources/complex1.json";
        String filePath2 = "src/test/resources/complex2.json";

        String expected = "{\n"
                + "    chars1: [a, b, c]\n"
                + "  - chars2: [d, e, f]\n"
                + "  + chars2: false\n"
                + "  - checked: false\n"
                + "  + checked: true\n"
                + "  - default: null\n"
                + "  + default: [value1, value2]\n"
                + "  - id: 45\n" + "  + id: null\n"
                + "  - key1: value1\n"
                + "  + key2: value2\n"
                + "    numbers1: [1, 2, 3, 4]\n"
                + "  - numbers2: [2, 3, 4, 5]\n"
                + "  + numbers2: [22, 33, 44, 55]\n"
                + "  - numbers3: [3, 4, 5]\n"
                + "  + numbers4: [4, 5, 6]\n"
                + "  + obj1: {nestedKey=value, isNested=true}\n"
                + "  - setting1: Some value\n"
                + "  + setting1: Another value\n"
                + "  - setting2: 200\n"
                + "  + setting2: 300\n"
                + "  - setting3: true\n"
                + "  + setting3: none\n"
                + "}";

        String result = Differ.generate(filePath1, filePath2);
        System.out.println(result);
        assertEquals(expected, result);
    }
}

