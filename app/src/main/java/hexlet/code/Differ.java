package hexlet.code;

import java.util.Map;

public class Differ {

    public static String  generate(String filePath1, String filePath2) throws Exception {
        Map<String, Object> data1 = Parser.parseData(filePath1);
        Map<String, Object> data2 = Parser.parseData(filePath2);

        return Tree.collect(data1, data2);
    }
}
