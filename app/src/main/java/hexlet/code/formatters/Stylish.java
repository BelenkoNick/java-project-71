package hexlet.code.formatters;

import hexlet.code.enums.ChangesType;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Function;

public class Stylish {

    private static final int SPACES_COUNT = 4;

    // отступ формируем согласно заданию: 4 пробела по умолчанию
    // не учитывая маркер 2 знака ('+ ', '- ', '  ')
    private static String buildIndent() {
        return " ".repeat(SPACES_COUNT - 2);
    }

    // Тип результата всегда должен быть строкой.
    private static String stringify(Object value) {
        if (value == null) {
            return "null";
        }

        return value.toString();
    }

    // Форматер должен выставлять только тот интерфейс, который ожидает клиент
    public static String render(List<Map<String, Object>> diff) {
        String indent = buildIndent();

        Function<Map<String, Object>, String> stringifyNode = node -> {
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
        };

        String output = diff.stream()
                .map(stringifyNode)
                .collect(Collectors.joining("\n"));

        return "{\n" + output + "\n}";
    }
}
