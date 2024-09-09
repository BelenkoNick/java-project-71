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
        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithComplexNestedFilesPlainFormat() throws Exception {
        String filePath1 = "src/test/resources/complex1.json";
        String filePath2 = "src/test/resources/complex2.json";

        String expected = "Property 'chars2' was updated. From [complex value] to false\n"
                + "Property 'checked' was updated. From false to true\n"
                + "Property 'default' was updated. From null to [complex value]\n"
                + "Property 'id' was updated. From 45 to null\n"
                + "Property 'key1' was removed\n"
                + "Property 'key2' was added with value: 'value2'\n"
                + "Property 'numbers2' was updated. From [complex value] to [complex value]\n"
                + "Property 'numbers3' was removed\n"
                + "Property 'numbers4' was added with value: [complex value]\n"
                + "Property 'obj1' was added with value: [complex value]\n"
                + "Property 'setting1' was updated. From 'Some value' to 'Another value'\n"
                + "Property 'setting2' was updated. From 200 to 300\n"
                + "Property 'setting3' was updated. From true to 'none'";

        String result = Differ.generate(filePath1, filePath2, "plain");
        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithComplexNestedFilesJsonFormat() throws Exception {
        String filePath1 = "src/test/resources/complex1.json";
        String filePath2 = "src/test/resources/complex2.json";

        String expected = "[{\"type\":\"UNTOUCHED\",\"value\":[\"a\",\"b\",\"c\"],\"key\":\"chars1\"},"
                + "{\"value2\":false,\"value1\":[\"d\",\"e\",\"f\"],\"type\":\"CHANGED\",\"key\":\"chars2\"},"
                + "{\"value2\":true,\"value1\":false,\"type\":\"CHANGED\",\"key\":\"checked\"},"
                + "{\"value2\":[\"value1\",\"value2\"],\"value1\":null,\"type\":\"CHANGED\",\"key\":\"default\"},"
                + "{\"value2\":null,\"value1\":45,\"type\":\"CHANGED\",\"key\":\"id\"},"
                + "{\"type\":\"DELETED\",\"value\":\"value1\",\"key\":\"key1\"},"
                + "{\"type\":\"ADDED\",\"value\":\"value2\",\"key\":\"key2\"},"
                + "{\"type\":\"UNTOUCHED\",\"value\":[1,2,3,4],\"key\":\"numbers1\"},"
                + "{\"value2\":[22,33,44,55],\"value1\":[2,3,4,5],\"type\":\"CHANGED\",\"key\":\"numbers2\"},"
                + "{\"type\":\"DELETED\",\"value\":[3,4,5],\"key\":\"numbers3\"},"
                + "{\"type\":\"ADDED\",\"value\":[4,5,6],\"key\":\"numbers4\"},"
                + "{\"type\":\"ADDED\",\"value\":{\"nestedKey\":\"value\",\"isNested\":true},\"key\":\"obj1\"},"
                + "{\"value2\":\"Another value\",\"value1\":\"Some value\",\"type\":\"CHANGED\",\"key\":\"setting1\"},"
                + "{\"value2\":300,\"value1\":200,\"type\":\"CHANGED\",\"key\":\"setting2\"},"
                + "{\"value2\":\"none\",\"value1\":true,\"type\":\"CHANGED\",\"key\":\"setting3\"}]";

        String result = Differ.generate(filePath1, filePath2, "json");
        assertEquals(expected, result);
    }
}

