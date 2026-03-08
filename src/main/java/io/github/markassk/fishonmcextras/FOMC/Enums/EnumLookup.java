package io.github.markassk.fishonmcextras.FOMC.Enums;

import io.github.markassk.fishonmcextras.FOMC.Types.Defaults;

import java.util.*;

public class EnumLookup<E extends EnumConstant> {
    protected final LinkedHashMap<String, E> idMap = new LinkedHashMap<>();
    protected final Map<String, E> tagMap = new HashMap<>();

    public final E unknownEntry;

    public EnumLookup(E[] entries) {
        E unknownEntry = null;
        for (E entry : entries) {
            if (entry.isUnknown()) {
                if (unknownEntry != null) {
                    throw new RuntimeException("Duplicate unknown entry");
                }
                unknownEntry = entry;
            } else {
                this.register(entry);
            }
        }
        if (unknownEntry == null) {
            throw new RuntimeException("No unknown entry");
        }
        this.unknownEntry = unknownEntry;
    }

    public void register(E entry) {
        if (this.idMap.put(entry.id(), entry) != null) {
            throw new RuntimeException("Duplicate ID (" + entry.id() + ")");
        }
        this.tagMap.putIfAbsent(entry.tag().getString(), entry);
    }

    public SequencedCollection<E> getAll() {
        return this.idMap.sequencedValues();
    }

    public E valueOfId(String id) {
        return this.idMap.getOrDefault(id, this.unknownEntry);
    }

    public E valueOfTag(String tag) {
        return this.tagMap.getOrDefault(tag, this.unknownEntry);
    }

    public E findContainingTag(String string) {
        for (var e : this.tagMap.entrySet()) {
            if (string.contains(e.getKey())) {
                return e.getValue();
            }
        }
        return this.unknownEntry;
    }
}
