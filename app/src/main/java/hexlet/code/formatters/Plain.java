package hexlet.code.formatters;

import hexlet.code.enums.ChangesType;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Function;

public class Plain {

    public static String render(List<Map<String, Object>> diff) {
        Function<Map<String, Object>, String> stringifyNode = node -> {
            ChangesType type = (ChangesType) node.get("type");
            String key = (String) node.get("key");
            String formattedValue = stringify(node.get("value"));
            String formattedValue1 = stringify(node.get("value1"));
            String formattedValue2 = stringify(node.get("value2"));

            return switch (type) {
                case ADDED -> "Property '" + key + "' was added" + " with value: " + formattedValue;
                case DELETED -> "Property '" + key + "' was removed";
                case CHANGED -> "Property '" + key + "' was updated."
                        + " From " + formattedValue1 + " to " + formattedValue2;
                case UNTOUCHED -> "";
            };
        };

        return diff.stream()
                .map(stringifyNode)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    private static String stringify(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof String && !isInteger(value) && !isBoolean(value)) {
            return "'" + value + "'";
        }

        if (value instanceof Map || value instanceof List) {
            return "[complex value]";
        }

        return value.toString();
    }

    private static boolean isInteger(Object value) {
        try {
            Integer.parseInt((String) value);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    private static boolean isBoolean(Object value) {
        return "true".equals(value) || "false".equals(value);
    }
}
