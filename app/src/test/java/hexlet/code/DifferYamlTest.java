package hexlet.code;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DifferYamlTest {

    @Test
    void testGenerateWithIdenticalYamlFiles() throws Exception {
        String filePath1 = "src/test/resources/examples/identical1.yaml";

        String expected = "{\n"
                + "    host: hexlet.io\n"
                + "    timeout: 50\n"
                + "}";

        String result = Differ.generate(filePath1, filePath1);
        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithMissingYamlFile() {
        String filePath1 = "src/test/resources/examples/existing.yaml";
        String filePath2 = "src/test/resources/examples/non_existing.yaml";

        Exception exception = assertThrows(Exception.class, () -> {
            Differ.generate(filePath1, filePath2);
        });

        String expectedMessage = " does not exist.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGenerateWithComplexNestedFilesStylishFormat() throws Exception {
        String filePath1 = "src/test/resources/examples/complex1.yaml";
        String filePath2 = "src/test/resources/examples/complex2.yaml";
        String expectedFilePath = "src/test/resources/expected/result_stylish.txt";

        String expected = Files.readString(Paths.get(expectedFilePath)
                .toAbsolutePath().normalize(), StandardCharsets.UTF_8);

        String result = Differ.generate(filePath1, filePath2, "stylish");
        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithComplexNestedFilesPlainFormat() throws Exception {
        String filePath1 = "src/test/resources/examples/complex1.yaml";
        String filePath2 = "src/test/resources/examples/complex2.yaml";
        String expectedFilePath = "src/test/resources/expected/result_plain.txt";

        String expected = Files.readString(Paths.get(expectedFilePath)
                .toAbsolutePath().normalize(), StandardCharsets.UTF_8);

        String result = Differ.generate(filePath1, filePath2, "plain");
        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithComplexNestedFilesJsonFormat() throws Exception {
        String filePath1 = "src/test/resources/examples/complex1.yaml";
        String filePath2 = "src/test/resources/examples/complex2.yaml";
        String expectedFilePath = "src/test/resources/expected/result_yaml.json";

        String expected = Files.readString(Paths.get(expectedFilePath)
                .toAbsolutePath().normalize(), StandardCharsets.UTF_8);

        String result = Differ.generate(filePath1, filePath2, "json");
        assertEquals(expected, result);
    }
}
