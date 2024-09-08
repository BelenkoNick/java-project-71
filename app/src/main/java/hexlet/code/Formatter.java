package hexlet.code;

import java.util.List;
import java.util.Map;

import hexlet.code.formatters.Stylish;

public class Formatter {

    public static String format(List<Map<String, Object>> diff, String formatName) throws Exception {
        if (formatName.equals("stylish")) {
            return Stylish.render(diff);
        }
        throw new Exception("Unknown format: '" + formatName + "'");
    }
}
