/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.util.Int2IntBiMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Int2IntMapBiMappings
implements BiMappings {
    private final Int2IntBiMap mappings;
    private final Int2IntMapBiMappings inverse;

    protected Int2IntMapBiMappings(Int2IntBiMap int2IntBiMap) {
        this.mappings = int2IntBiMap;
        this.inverse = new Int2IntMapBiMappings(int2IntBiMap.inverse(), this);
        int2IntBiMap.defaultReturnValue(-1);
    }

    private Int2IntMapBiMappings(Int2IntBiMap int2IntBiMap, Int2IntMapBiMappings int2IntMapBiMappings) {
        this.mappings = int2IntBiMap;
        this.inverse = int2IntMapBiMappings;
    }

    public static Int2IntMapBiMappings of(Int2IntBiMap int2IntBiMap) {
        return new Int2IntMapBiMappings(int2IntBiMap);
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
        return this.mappings.inverse().size();
    }

    @Override
    public BiMappings inverse() {
        return this.inverse;
    }

    @Override
    public Mappings inverse() {
        return this.inverse();
    }
}

