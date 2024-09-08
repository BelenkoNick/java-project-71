package hexlet.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableSortedSet;
import hexlet.code.enums.ChangesType;

public class Tree {
    public static List<Map<String, Object>> build(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> keys = Sets.union(data1.keySet(), data2.keySet());
        Set<String> sortedKeys = ImmutableSortedSet.copyOf(keys);
        return sortedKeys.stream()
            .map(key -> {
                Object value1 = data1.get(key);
                Object value2 = data2.get(key);

                Map<String, Object> node = new HashMap<>();
                node.put("key", key);

                if (!data2.containsKey(key)) {
                    node.put("type", ChangesType.DELETED);
                    node.put("value", value1);
                } else if (!data1.containsKey(key)) {
                    node.put("type", ChangesType.ADDED);
                    node.put("value", value2);
                } else if (!Objects.equals(value1, value2)) {
                    node.put("type", ChangesType.CHANGED);
                    node.put("value1", value1);
                    node.put("value2", value2);
                } else {
                    node.put("type", ChangesType.UNTOUCHED);
                    node.put("value", value1);
                }
                return node;
            })
            .collect(Collectors.toList());
    }
}

