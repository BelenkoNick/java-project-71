package hexlet.code;

import java.util.List;
import java.util.Map;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

public class Formatter {

    public static String format(List<Map<String, Object>> diff, String formatName) throws Exception {
        return switch (formatName) {
            case "stylish" -> Stylish.render(diff);
            case "plain" -> Plain.render(diff);
            case "json" -> Json.render(diff);
            default -> throw new Exception("Unknown format: '" + formatName + "'");
        };
    }
}
