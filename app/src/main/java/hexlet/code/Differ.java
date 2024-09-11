package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Differ {

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, "stylish");
    }

    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {
        Map<String, Object> data1 = getData(filePath1);
        Map<String, Object> data2 = getData(filePath2);
        return Formatter.format(Tree.build(data1, data2), formatName);
    }

    private static Map<String, Object> getData(String filePath) throws Exception {
        Path fullPath = Paths.get(filePath).toAbsolutePath().normalize();

        if (!Files.exists(fullPath)) {
            throw new Exception("File " + fullPath + " does not exist.");
        }

        String content = Files.readString(fullPath);
        String dataFormat = filePath.lastIndexOf('.') > 0
                ? filePath.substring(filePath.lastIndexOf('.') + 1) : "";

        return Parser.parse(content, dataFormat);
    }
}
