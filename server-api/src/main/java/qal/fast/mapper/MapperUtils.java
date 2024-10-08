package qal.fast.mapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface MapperUtils {

    default List<String> splitStrings2List(String splitStr) {
        if (splitStr == null || splitStr.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(splitStr.split(","));
    }

    // list to split String
    default String list2SplitStrings(List<String> stringList) {
        if (stringList == null || stringList.isEmpty()) {
            return null;
        }
        return String.join(",", stringList);
    }
}
