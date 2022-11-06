package ru.mosit.pahotest.parsing;

import java.util.ArrayList;
import java.util.List;

record Entry(String key, String value) {
}

public class Data {
    private final List<Entry> entries = new ArrayList<>();

    public void add(String key, String val) {
        entries.add(new Entry(key, val));
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Entry entry : entries) {
            out.append(entry.key()).append(" = ").append(entry.value()).append("\n");
        }
        return out.toString();
    }
}
