package by.bsuir.ootpsp.task1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TestCollections<TKey, TValue> {

    private Function<Integer, Map.Entry<TKey, TValue>> entryGenerator;

    private List<TKey> tKeys = new ArrayList<>();

    private List<String> strings = new ArrayList<>();

    private Map<TKey, TValue> keyValueMap = new HashMap<>();

    private Map<String, TValue> stringValueMap = new HashMap<>();

    public TestCollections(int collectionsSize, Function<Integer, Map.Entry<TKey, TValue>> entryGenerator) {
        this.entryGenerator = entryGenerator;
        for (int i = 0; i < collectionsSize; i++) {
            Map.Entry<TKey, TValue> entry = this.entryGenerator.apply(i);
            tKeys.add(entry.getKey());
            strings.add(entry.getKey().toString());
            keyValueMap.put(entry.getKey(), entry.getValue());
            stringValueMap.put(entry.getKey().toString(), entry.getValue());
        }
    }

    public long[] getSearchTimeForEachCollection(int searchElementIndex) {
        Map.Entry<TKey, TValue> searchEntry = this.entryGenerator.apply(searchElementIndex);
        long[] searchTimeArray = new long[5];
        searchTimeArray[0] = getExecutionTime(() -> tKeys.contains(searchEntry.getKey()));
        searchTimeArray[1] = getExecutionTime(() -> strings.contains(searchEntry.getKey().toString()));
        searchTimeArray[2] = getExecutionTime(() -> keyValueMap.containsKey(searchEntry.getKey()));
        searchTimeArray[3] = getExecutionTime(() -> stringValueMap.containsKey(searchEntry.getKey().toString()));
        searchTimeArray[4] = getExecutionTime(() -> keyValueMap.containsValue(searchEntry.getValue()));

        return searchTimeArray;
    }

    private long getExecutionTime(Action action) {
        long startTime = System.nanoTime();
        action.execute();
        long endTime = System.nanoTime();

        return endTime - startTime;
    }

    @FunctionalInterface
    private interface Action {

        void execute();

    }

}
