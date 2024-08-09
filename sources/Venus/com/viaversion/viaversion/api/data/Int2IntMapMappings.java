/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;

public class Int2IntMapMappings
implements Mappings {
    private final Int2IntMap mappings;
    private final int mappedIds;

    protected Int2IntMapMappings(Int2IntMap int2IntMap, int n) {
        this.mappings = int2IntMap;
        this.mappedIds = n;
        int2IntMap.defaultReturnValue(-1);
    }

    public static Int2IntMapMappings of(Int2IntMap int2IntMap, int n) {
        return new Int2IntMapMappings(int2IntMap, n);
    }

    public static Int2IntMapMappings of() {
        return new Int2IntMapMappings(new Int2IntOpenHashMap(), -1);
    }

    @Override
    public int getNewId(int n) {
        return this.mappings.get(n);
    }

    @Override
    public void setNewId(int n, int n2) {
        this.mappings.put(n, n2);
    }

    @Override
    public int size() {
        return this.mappings.size();
    }

    @Override
    public int mappedSize() {
        return this.mappedIds;
    }

    @Override
    public Mappings inverse() {
        Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap();
        int2IntOpenHashMap.defaultReturnValue(-1);
        for (Int2IntMap.Entry entry : this.mappings.int2IntEntrySet()) {
            if (entry.getIntValue() == -1) continue;
            int2IntOpenHashMap.putIfAbsent(entry.getIntValue(), entry.getIntKey());
        }
        return Int2IntMapMappings.of(int2IntOpenHashMap, this.size());
    }
}

