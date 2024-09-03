package hexlet.code;

import java.util.Map;
import java.util.Set;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableSortedSet;

public class Tree {

    public static String collect(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> keys = Sets.union(data1.keySet(), data2.keySet());
        Set<String> sortedKeys = ImmutableSortedSet.copyOf(keys);

        StringBuilder result = new StringBuilder("{\n");

        for (String key : sortedKeys) {
            Object value1 = data1.get(key);
            Object value2 = data2.get(key);

            if (!data2.containsKey(key)) { // удалённое поле
                result.append("  - ").append(key).append(": ").append(value1).append("\n");
            } else if (!data1.containsKey(key)) { // добавленное поле
                result.append("  + ").append(key).append(": ").append(value2).append("\n");
            } else if (!value1.equals(value2)) { // изменённое поле
                result.append("  - ").append(key).append(": ").append(value1).append("\n");
                result.append("  + ").append(key).append(": ").append(value2).append("\n");
            } else { // неизменённое поле
                result.append("    ").append(key).append(": ").append(value1).append("\n");
            }
        }

        result.append("}");
        return result.toString();
    }
}

