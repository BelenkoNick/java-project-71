package hexlet.code;

import java.util.Map;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Differ {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static Map<String, Object> parseData(String filePath) throws Exception {
        Path fullPath = Paths.get(filePath).toAbsolutePath().normalize();

        if (!Files.exists(fullPath)) {
            throw new Exception("File " + fullPath + " does not exist.");
        }

        String content = Files.readString(fullPath);
        return mapper.readValue(content, Map.class);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        Map<String, Object> data1 = parseData(filePath1);
        Map<String, Object> data2 = parseData(filePath2);

        return Tree.collect(data1, data2);
    }
}
