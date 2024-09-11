package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String content, String dataFormat) throws Exception {
        return switch (dataFormat) {
            case "yml", "yaml" -> parseYaml(content);
            case "json" -> parseJson(content);
            default -> throw new Exception("Unknown format: '" + dataFormat + "'");
        };
    }

    private static Map<String, Object> parseYaml(String content) throws Exception  {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference<Map<String, Object>> type = new TypeReference<Map<String, Object>>() { };
        return mapper.readValue(content, type);
    }

    private static Map<String, Object> parseJson(String content) throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, Object>> type = new TypeReference<>() { };
        return mapper.readValue(content, type);
    }
}
