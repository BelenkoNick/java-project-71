package hexlet.code.formatters;

import hexlet.code.enums.ChangesType;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class Stylish {

    private static final int SPACES_COUNT = 4;
    private static final int INDENT_COUNT = 2;

    public static String render(List<Map<String, Object>> diff) {
        String indent = " ".repeat(SPACES_COUNT - INDENT_COUNT);
        String output = diff.stream()
                .map(node -> {
                    ChangesType type = (ChangesType) node.get("type");
                    String key = (String) node.get("key");
                    String formattedValue = stringify(node.get("value"));
                    String formattedValue1 = stringify(node.get("value1"));
                    String formattedValue2 = stringify(node.get("value2"));

                    // Принципиально что не должно быть особых нод,
                    // обрабатываемых не так как все остальные (вне switch например).
                    return switch (type) {
                        case ADDED -> indent + "+ " + key + ": " + formattedValue;
                        case DELETED -> indent + "- " + key + ": " + formattedValue;
                        case CHANGED -> indent + "- " + key + ": " + formattedValue1 + "\n"
                                + indent + "+ " + key + ": " + formattedValue2;
                        case UNTOUCHED -> indent + "  " + key + ": " + formattedValue;
                    };
                })
                .collect(Collectors.joining("\n"));

        return "{\n" + output + "\n}";
    }

    private static String stringify(Object value) {
        if (value == null) {
            return "null";
        }
        return value.toString();
    }
}
